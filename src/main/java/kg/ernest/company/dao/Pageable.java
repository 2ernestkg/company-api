package kg.ernest.company.dao;

import java.util.List;

/**
 * Created by ekuttubaev on 24.03.16.
 */
public class Pageable<T> {
  private List<T> data;
  private long counts;

  public Pageable(List<T> list, long totalCount) {
    this.data = list;
    this.counts = totalCount;
  }

  public List<T> getData() {
    return data;
  }

  public long getCounts() {
    return counts;
  }
}
