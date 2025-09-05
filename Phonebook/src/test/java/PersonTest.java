import application.main.model.entity.Address;
import application.main.model.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest
{
  private Person personTest;

  @BeforeEach
  public void setUp()
  {
      personTest = new Person()
              .setAge(25)
              .setName("Melanie")
              .setId(1)
              .setPermanentAddressId(1);
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

  public void temporary_address_can_be_set()
  {
    personTest.setTemporaryAddressId(1);

    Assertions.assertEquals(1, personTest.getTemporaryAddressId());
  }
}
