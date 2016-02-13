package kg.ernest.company.dao.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
@JsonDeserialize(using = CompanyJsonDeserializer.class)
public class Company implements Serializable {
  public static final String NAME_KEY = "name";
  public static final String ID_KEY = "id";
  public static final String OWNERS_KEY = "owners";
  @Id
  @GeneratedValue
  @Column(name = ID_KEY)
  private int id;
  @Column(name = NAME_KEY, nullable = false, unique = true)
  private String name;
  @JsonUnwrapped
  @Embedded
  private Address address;
  @JsonUnwrapped
  @Embedded
  @Valid
  private ContactDetail contactDetail;
  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST})
  @JoinTable(
          name = "company_owners",
          joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "owner_name", referencedColumnName = "name")
  )
  private List<Beneficiar> owners = new ArrayList<Beneficiar>();

  public Company() {
    //default constructor
  }

  private Company(String name, Address address, ContactDetail contactDetail, List<Beneficiar> owners) {
    this.name = name;
    this.address = address;
    this.contactDetail = contactDetail;
    this.owners = owners;
  }

  public Company(String name, Address address, List<Beneficiar> owners) {
    this(name, address, null, owners);
  }

  public Company(String name, Address address) {
    this(name, address, null, new ArrayList<Beneficiar>());
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public ContactDetail getContactDetail() {
    return contactDetail;
  }

  public void setContactDetail(ContactDetail contactDetail) {
    this.contactDetail = contactDetail;
  }

  public List<Beneficiar> getOwners() {
    return owners;
  }

  public void addOwner(Beneficiar owner) {
    if (!owners.contains(owner)) {
      this.owners.add(owner);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Company)) return false;

    Company company = (Company) o;

    if (id != company.id) return false;
    if (name != null ? !name.equals(company.name) : company.name != null) return false;
    return !(owners != null ? !owners.equals(company.owners) : company.owners != null);

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (owners != null ? owners.hashCode() : 0);
    return result;
  }
}
