package kg.ernest.company.dao;

import kg.ernest.company.dao.model.Beneficiar;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class BeneficiarDao {
  @PersistenceContext
  private EntityManager em;

  public Beneficiar get(String name) {
    return em.find(Beneficiar.class, name);
  }

  @Transactional
  public void save(Beneficiar beneficiar) {
    em.persist(beneficiar);
  }
}
