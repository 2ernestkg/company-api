package kg.ernest.company.dao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kg.ernest.company.validator.Email;
import kg.ernest.company.validator.Phone;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.io.Serializable;

@Embeddable
public class ContactDetail implements Serializable {
  public static final String EMAIL_KEY = "email";
  public static final String PHONE_NUMBER_KEY = "phone_number";
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = EMAIL_KEY)
  @Email
  private String email;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty(value = PHONE_NUMBER_KEY)
  @Column(name = PHONE_NUMBER_KEY)
  @Phone
  @Min(5)
  @Max(150)
  private String phoneNumber;

  public ContactDetail() {
    //default constructor
  }

  public ContactDetail(String email, String phoneNumber) {
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
