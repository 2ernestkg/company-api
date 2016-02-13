package kg.ernest.company.dao.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyJsonDeserializer extends JsonDeserializer<Company> {
  @Override
  public Company deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    int id = 0;
    if (node.has(Company.ID_KEY)) {
      id = (Integer) ((IntNode) node.get(Company.ID_KEY)).numberValue();
    }
    String name = node.get(Company.NAME_KEY).asText();
    String address = node.get(Address.ADDRESS_KEY).asText();
    String city = node.get(Address.CITY_KEY).asText();
    String country = node.get(Address.COUNTRY_KEY).asText();
    String email = null;
    if (node.has(ContactDetail.EMAIL_KEY)) {
      email = node.get(ContactDetail.EMAIL_KEY).asText();
    }
    String phoneNumber = null;
    if (node.has(ContactDetail.PHONE_NUMBER_KEY)) {
      phoneNumber = node.get(ContactDetail.PHONE_NUMBER_KEY).asText();
    }
    ArrayNode ownersNode = (ArrayNode) node.get(Company.OWNERS_KEY);
    List<Beneficiar> owners = new ArrayList<Beneficiar>();
    if (ownersNode != null && ownersNode.isArray()) {
      for (final JsonNode ownerNode : ownersNode) {
        String beneficiarName = ownerNode.get(Person.NAME_KEY).asText();
        owners.add(new Beneficiar(beneficiarName));
      }
    }
    Company company = new Company(name, new Address(city, country, address), owners);
    company.setId(id);
    company.setContactDetail(new ContactDetail(email, phoneNumber));
    return company;
  }
}
