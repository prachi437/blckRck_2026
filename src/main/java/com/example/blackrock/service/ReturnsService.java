
package com.example.blackrock.service;

import com.example.blackrock.domain.*;
import com.example.blackrock.domain.dto.*;
import com.example.blackrock.util.DateUtil;
import com.example.blackrock.util.TaxUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReturnsService {

  private static final double NPS_RATE = 0.0711;
  private static final double INDEX_RATE = 0.1449;

  public ReturnsResponse computeNps(ReturnsRequest req, TemporalService temporal, TransactionService txService) {
    FilterResponse filtered = temporal.applyPeriods(req.getQ(), req.getP(), req.getK(), req.getTransactions());
    List<Transaction> valid = filtered.getValid();

    double totalAmount = valid.stream().mapToDouble(Transaction::getAmount).sum();
    double totalCeiling = valid.stream().mapToDouble(Transaction::getCeiling).sum();

    Map<PeriodK, Double> acc = temporal.groupByK(req.getK(), valid);
    List<ReturnsResponse.SavingByDates> sbd = new ArrayList<>();
    for (PeriodK k : req.getK()) {
      sbd.add(new ReturnsResponse.SavingByDates(
          DateUtil.format(k.getStart()), DateUtil.format(k.getEnd()), round2(acc.get(k))));
    }

    double invested = acc.values().stream().mapToDouble(Double::doubleValue).sum();

    int tYears = (req.getAge() < 60) ? (60 - req.getAge()) : 5;
    double A = invested * Math.pow(1 + NPS_RATE, tYears);

    double annualIncome = req.getWage() * 12.0;
    double npsDeduction = Math.min(invested, Math.min(0.10 * annualIncome, 200000.0));

    double taxBefore = TaxUtil.tax(annualIncome);
    double taxAfter = TaxUtil.tax(Math.max(0, annualIncome - npsDeduction));
    double taxBenefit = taxBefore - taxAfter;

    double profits = A - invested;

    return new ReturnsResponse(round2(totalAmount), round2(totalCeiling), sbd, round2(profits), round2(taxBenefit));
  }

  public ReturnsResponse computeIndex(ReturnsRequest req, TemporalService temporal, TransactionService txService) {
    FilterResponse filtered = temporal.applyPeriods(req.getQ(), req.getP(), req.getK(), req.getTransactions());
    List<Transaction> valid = filtered.getValid();

    double totalAmount = valid.stream().mapToDouble(Transaction::getAmount).sum();
    double totalCeiling = valid.stream().mapToDouble(Transaction::getCeiling).sum();

    Map<PeriodK, Double> acc = temporal.groupByK(req.getK(), valid);
    List<ReturnsResponse.SavingByDates> sbd = new ArrayList<>();
    for (PeriodK k : req.getK()) {
      sbd.add(new ReturnsResponse.SavingByDates(
          DateUtil.format(k.getStart()), DateUtil.format(k.getEnd()), round2(acc.get(k))));
    }

    double invested = acc.values().stream().mapToDouble(Double::doubleValue).sum();
    int tYears = (req.getAge() < 60) ? (60 - req.getAge()) : 5;
    double A = invested * Math.pow(1 + INDEX_RATE, tYears);
    double profits = A - invested;

    return new ReturnsResponse(round2(totalAmount), round2(totalCeiling), sbd, round2(profits), 0.0);
  }

  private double round2(double v) { return Math.round(v * 100.0) / 100.0; }
}
