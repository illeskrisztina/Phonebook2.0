import application.main.model.database.dao.AddressDAO;
import application.main.model.entity.Address;
import application.main.model.entity.Person;
import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest
{
  private Person personTest;
    private Address temporaryAddress;

  @BeforeEach
  public void setUp()
  {
      personTest = new Person()
              .setAge(25)
              .setName("Melanie")
              .setId(1)
              .setPermanentAddress(new Address());
      temporaryAddress = new Address()
              .setType(AddressType.TEMPORARY)
              .setResidence("other address");
  }

  @Test
  public void name_can_be_set()
  {
    personTest.setName("David");

    Assertions.assertEquals("David", personTest.getName());
  }

  @Test
  public void age_can_be_set()
  {
    personTest.setAge(55);

    Assertions.assertEquals(55, personTest.getAge());
  }

  @Test
  public void id_can_be_set()
  {
    personTest.setId(4);

    Assertions.assertEquals(4, personTest.getId());
  }

  @Test
  public void temporary_address_can_be_set()
  {
    personTest.setTemporaryAddress(new Address()
            .setType(AddressType.TEMPORARY)
            .setResidence("other address"));

    Assertions.assertEquals(temporaryAddress, personTest.getTemporaryAddress());
  }
}
