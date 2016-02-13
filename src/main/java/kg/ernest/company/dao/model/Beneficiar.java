package kg.ernest.company.dao.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.List;

@Entity
@Table(name = "beneficiar")
public class Beneficiar extends Person {
  public Beneficiar() {
    //default constructor
  }

  public Beneficiar(String name) {
    this.name = name;
  }

  //companies where this person is beneficiar
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "owners")
  private List<Company> companies;
}
