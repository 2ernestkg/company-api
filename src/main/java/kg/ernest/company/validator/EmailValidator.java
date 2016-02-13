package kg.ernest.company.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String>{
  private String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
  @Override
  public void initialize(Email paramA) {
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext ctx) {
    if (email == null){
      return true;
    }

    return email.matches(regex);
  }
}
