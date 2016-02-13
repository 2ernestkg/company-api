package kg.ernest.company.dao.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Address implements Serializable {
  public static final String ADDRESS_KEY = "address";
  public static final String CITY_KEY = "city";
  public static final String COUNTRY_KEY = "country";
  @Column(name = ADDRESS_KEY, nullable = false)
  private String address;
  @Column(name = CITY_KEY, nullable = false)
  private String city;
  @Column(name = COUNTRY_KEY, nullable = false)
  private String country;

  public Address() {
    //default constructor
  }

  public Address(String city, String country, String address) {
    this.city = city;
    this.country = country;
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }
}
