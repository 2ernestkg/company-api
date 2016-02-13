package kg.ernest.company.dao;

import kg.ernest.company.dao.model.Address;
import kg.ernest.company.dao.model.Beneficiar;
import kg.ernest.company.dao.model.Company;
import kg.ernest.company.dao.model.ContactDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class CompanyDaoTest {
  @Autowired
  private CompanyDao companyDao;
  @Autowired
  private BeneficiarDao beneficiarDao;

  @Test
  @Transactional
  @Rollback(true)
  public void testSave() {
    Address address = new Address("San-Francisco", "USA", "Bay aread 52");
    Company company = new Company("Google", address);

    companyDao.save(company);

    List<Company> companies = companyDao.getList();
    assertThat("There must be one record of company", companies.size(), is(1));

    Company savedComp = companies.get(0);
    assertThat("Saved company must have assigned id", savedComp.getId() > 0);
    assertThat("Saved company name is the same as passed", savedComp.getName(), equalTo(company.getName()));
    assertThat("Saved company country is the same as passed", savedComp.getAddress().getCountry(),
            equalTo(company.getAddress().getCountry()));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void testUpdate() {
    Address address = new Address("San-Francisco", "USA", "Bay aread 52");
    Company company = new Company("Google", address);

    companyDao.save(company);

    Company savedCompany = companyDao.get(company.getId());

    savedCompany.setContactDetail(new ContactDetail("support@google.com", "+72 839 32 23"));

    companyDao.save(savedCompany);

    Company updatedCompany = companyDao.get(company.getId());
    assertThat("Company email must be the same as updated", updatedCompany.getContactDetail().getEmail(),
            equalTo(savedCompany.getContactDetail().getEmail()));
    assertThat("Company phone number must be the same as updated", updatedCompany.getContactDetail().getPhoneNumber(),
            equalTo(savedCompany.getContactDetail().getPhoneNumber()));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void testCascadePersistBeneficiar() {
    Address address = new Address("San-Francisco", "USA", "Bay aread 52");
    Company company = new Company("Google", address);
    company.addOwner(new Beneficiar("Larry Page"));

    companyDao.save(company);

    Company savedCompany = companyDao.get(company.getId());

    assertThat("Company was saved with one owner", savedCompany.getOwners().size(), is(1));
    assertThat("Company was saved with one owner, whos name is 'Larry Page'", savedCompany.getOwners().get(0).getName(),
            equalTo("Larry Page"));

    //If company is delete, beneficiar should remain
    companyDao.delete(savedCompany);

    Company deletedCompany = companyDao.get(savedCompany.getId());
    assertThat("Company has been deleted", deletedCompany == null);
    Beneficiar beneficiar = beneficiarDao.get("Larry Page");
    assertThat("Beneficiar exists", beneficiar != null);
  }

  @Test
  @Transactional
  @Rollback(true)
  public void testBeneficiarsCascadelyDetachedOnUpdate() {
    Address address = new Address("San-Francisco", "USA", "Bay aread 52");
    Company company = new Company("Google", address);
    company.addOwner(new Beneficiar("Larry Page"));
    company.addOwner(new Beneficiar("Sergey Brin"));

    companyDao.save(company);

    Company savedCompany = companyDao.get(company.getId());
    assertThat("Company was saved with two owner", savedCompany.getOwners().size(), is(2));

    //updating beneficiars
    savedCompany.getOwners().clear();
    savedCompany.addOwner(new Beneficiar("Larry Page"));
    companyDao.save(savedCompany);

    Company updatedCompany = companyDao.get(company.getId());
    assertThat("Company was updated to have only one owner", savedCompany.getOwners().size(), is(1));
  }


}
