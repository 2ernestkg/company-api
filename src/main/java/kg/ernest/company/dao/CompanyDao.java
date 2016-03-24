package kg.ernest.company.dao;

import kg.ernest.company.dao.model.Company;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

@Component
public class CompanyDao {
  private final static int DEFAULT_LIMIT = 100;
  @PersistenceContext
  private EntityManager em;

  @Transactional
  public Company save(Company company) {
    if (company.getId() == 0) {
      em.persist(company);
    } else {
      company = em.merge(company);
    }
    return company;
  }

  public Pageable<Company> getList() {
    return getList(0, DEFAULT_LIMIT);
  }

  public Pageable<Company> getList(int offset, int limit) {
    Query countQuery = em.createQuery("SELECT count(x.id) FROM Company x");

    long counts = ((Long) countQuery.getSingleResult()).longValue();

    Query query = em.createQuery("select company from Company company order by company.id asc");
    List<Company> list =query.setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();

    return new Pageable<Company>(list, counts);
  }

  public Company get(int id) {
    return em.find(Company.class, id);
  }


  public void delete(Company savedCompany) {
    em.remove(savedCompany);
  }
}
