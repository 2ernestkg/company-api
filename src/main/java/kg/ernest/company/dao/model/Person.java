package kg.ernest.company.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class Person implements Serializable {
  public static final String NAME_KEY = "name";
  @Id
  @Column(name = NAME_KEY, nullable = false, unique = true)
  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;

    Person person = (Person) o;

    return !(name != null ? !name.equals(person.name) : person.name != null);

  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
