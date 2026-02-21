
package com.example.blackrock.service;

import com.example.blackrock.domain.*;
import com.example.blackrock.domain.dto.FilterResponse;
import com.example.blackrock.util.DateUtil;
import com.example.blackrock.util.IntervalTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TemporalService {

  private static final Logger log = LoggerFactory.getLogger(TemporalService.class);

  public FilterResponse applyPeriods(java.util.List<PeriodQ> qList, java.util.List<PeriodP> pList, java.util.List<PeriodK> kList, java.util.List<Transaction> txs) {
    log.debug("filter: input transactionsCount={}", txs != null ? txs.size() : 0);
    FilterResponse resp = new FilterResponse();

    java.util.List<PeriodQ> qSorted = new java.util.ArrayList<>(qList);
    qSorted.sort((a,b) -> {
      int cmp = a.getStart().compareTo(b.getStart());
      if (cmp != 0) return cmp;
      return Integer.compare(a.getIdx(), b.getIdx());
    });

    java.util.List<PeriodP> pSorted = new java.util.ArrayList<>(pList);
    pSorted.sort(java.util.Comparator.comparing(PeriodP::getStart));

    for (Transaction t : txs) {
      double rem = t.getRemanent();

      PeriodQ chosen = findQ(qSorted, t.getDate());
      if (chosen != null) {
        rem = chosen.getFixed();
      }

      double add = sumP(pSorted, t.getDate());
      rem += add;

      if (rem < 0) {
        resp.getInvalid().add(new FilterResponse.InvalidTransaction(t, "negative remanent after periods"));
      } else {
        t.setRemanent(rem);
        resp.getValid().add(t);
      }
    }
    log.info("filter: completed, valid={}, invalid={}", resp.getValid().size(), resp.getInvalid().size());
    return resp;
  }

  private PeriodQ findQ(java.util.List<PeriodQ> qSorted, LocalDateTime d) {
    int lo = 0, hi = qSorted.size() - 1, best = -1;
    while (lo <= hi) {
      int mid = (lo + hi) >>> 1;
      if (!qSorted.get(mid).getStart().isAfter(d)) { best = mid; lo = mid + 1; }
      else hi = mid - 1;
    }
    if (best == -1) return null;
    for (int i = best; i >= 0; i--) {
      PeriodQ q = qSorted.get(i);
      if (q.getStart().isAfter(d)) break;
      if (!d.isBefore(q.getStart()) && !d.isAfter(q.getEnd())) {
        return q;
      }
    }
    for (int i = best + 1; i < qSorted.size() && !qSorted.get(i).getStart().isAfter(d); i++) {
      PeriodQ q = qSorted.get(i);
      if (!d.isBefore(q.getStart()) && !d.isAfter(q.getEnd())) return q;
    }
    return null;
  }

  private double sumP(java.util.List<PeriodP> pSorted, LocalDateTime d) {
    int lo = 0, hi = pSorted.size() - 1, idx = -1;
    while (lo <= hi) {
      int mid = (lo + hi) >>> 1;
      if (!pSorted.get(mid).getStart().isAfter(d)) { idx = mid; lo = mid + 1; }
      else hi = mid - 1;
    }
    if (idx == -1) return 0.0;
    double sum = 0.0;
    for (int i = idx; i >= 0; i--) {
      PeriodP p = pSorted.get(i);
      if (p.getStart().isAfter(d)) break;
      if (!d.isAfter(p.getEnd())) sum += p.getExtra();
    }
    return sum;
  }

  public java.util.Map<PeriodK, Double> groupByK(java.util.List<PeriodK> kList, java.util.List<Transaction> txs) {
    IntervalTree tree = new IntervalTree(kList);
    java.util.Map<PeriodK, Double> acc = new java.util.LinkedHashMap<>();
    for (PeriodK k : kList) acc.put(k, 0.0);

    for (Transaction t : txs) {
      java.util.List<PeriodK> hits = tree.stab(t.getDate());
      for (PeriodK k : hits) {
        acc.put(k, acc.get(k) + t.getRemanent());
      }
    }
    return acc;
  }
}
