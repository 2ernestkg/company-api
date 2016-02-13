package kg.ernest.company.rest;

import kg.ernest.company.dao.BeneficiarDao;
import kg.ernest.company.dao.CompanyDao;
import kg.ernest.company.dao.model.Beneficiar;
import kg.ernest.company.dao.model.Company;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/company")
public class CompanyRestController {
  private static final String JSON_TEMPLATE = "jsonTemplate";
  @Autowired
  private CompanyDao companyDao;
  @Autowired
  private BeneficiarDao beneficiarDao;

  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Company>> getList(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                               @RequestParam(value = "limit", defaultValue = "0") int limit) {
    if (limit != 0) {
      return new ResponseEntity<List<Company>>(companyDao.getList(offset, limit), HttpStatus.OK);
    }
    return new ResponseEntity<List<Company>>(companyDao.getList(), HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<Company> create(@RequestBody @Valid Company company, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    Company savedCompany = null;
    try {
      companyDao.save(company);
    } catch (Exception ex) {
      return handleException(ex);
    }
    return new ResponseEntity<Company>(savedCompany, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Company> get(@PathVariable int id) {
    Company company = companyDao.get(id);
    if (company == null) {
      new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Company>(company, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Company> update(@PathVariable int id,
                                        @RequestBody @Valid Company company, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    Company updateCompany = companyDao.get(id);
    if (updateCompany == null) {
      return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
    }
    company.setId(updateCompany.getId());
    try {
      companyDao.save(company);
    } catch (Exception ex) {
      return handleException(ex);
    }
    return new ResponseEntity<Company>(updateCompany, HttpStatus.ACCEPTED);
  }

  @RequestMapping(value = "/{id}/owner", method = RequestMethod.PUT)
  public ResponseEntity<Company> addOwner(@PathVariable int id, @RequestBody @Valid Beneficiar newBeneficiar,
                                          BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    Company company = companyDao.get(id);
    if (company == null) {
      return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
    }
    Beneficiar beneficiar = beneficiarDao.get(newBeneficiar.getName());
    if (beneficiar == null) {
      beneficiarDao.save(newBeneficiar);
      beneficiar = newBeneficiar;
    }
    company.addOwner(beneficiar);
    try {
      companyDao.save(company);
    } catch (Exception ex) {
      handleException(ex);
    }
    return new ResponseEntity<Company>(company, HttpStatus.ACCEPTED);
  }

  private ResponseEntity handleException(Exception ex) {
    Throwable cause = ex.getCause();

    if (cause instanceof EntityNotFoundException) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else if (cause instanceof ConstraintViolationException) {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }

    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}