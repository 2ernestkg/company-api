package kg.ernest.company.validator;

import kg.ernest.company.dao.model.Company;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CompanyValidator implements Validator {
  private final Validator phoneValidator;
  private final Validator emailValidator;

  public CompanyValidator(Validator emailValidator, Validator phoneValidator) {
    this.phoneValidator = phoneValidator;
    this.emailValidator = emailValidator;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return Company.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");

    Company company = (Company) o;
  }
}
