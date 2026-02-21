
package com.example.blackrock.util;

import com.example.blackrock.domain.PeriodK;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Centered interval tree for stabbing queries (find all K that contain a date).
public class IntervalTree {
  private static class Node {
    LocalDateTime center;
    List<PeriodK> spanning = new ArrayList<>();
    Node left, right;
  }
  private final Node root;

  public IntervalTree(List<PeriodK> intervals) {
    this.root = build(intervals);
  }

  private Node build(List<PeriodK> intervals) {
    if (intervals == null || intervals.isEmpty()) return null;
    intervals.sort((a,b) -> a.getStart().compareTo(b.getStart()));
    int mid = intervals.size() / 2;
    LocalDateTime center = intervals.get(mid).getStart();
    List<PeriodK> leftList = new ArrayList<>();
    List<PeriodK> rightList = new ArrayList<>();
    List<PeriodK> span = new ArrayList<>();
    for (PeriodK k : intervals) {
      if (k.getEnd().isBefore(center)) leftList.add(k);
      else if (k.getStart().isAfter(center)) rightList.add(k);
      else span.add(k);
    }
    Node n = new Node();
    n.center = center;
    n.spanning = span;
    n.left = build(leftList);
    n.right = build(rightList);
    return n;
  }

  public List<PeriodK> stab(LocalDateTime point) {
    List<PeriodK> out = new ArrayList<>();
    stab(root, point, out);
    return out;
  }
  private void stab(Node n, LocalDateTime p, List<PeriodK> out) {
    if (n == null) return;
    for (PeriodK k : n.spanning) {
      if (!p.isBefore(k.getStart()) && !p.isAfter(k.getEnd())) out.add(k);
    }
    if (p.isBefore(n.center)) stab(n.left, p, out);
    else if (p.isAfter(n.center)) stab(n.right, p, out);
    else {
      stab(n.left, p, out);
      stab(n.right, p, out);
    }
  }
}
