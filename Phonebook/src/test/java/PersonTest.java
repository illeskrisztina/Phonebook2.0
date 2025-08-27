import application.main.model.Entity.Address;
import application.main.model.Entity.Person;
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
              .setPermanentAddress(new Address()
                      .setAddress("Denmark, Aarhus"));
  }

  @Test
  public void setting_permanent_to_null_throws_error()
  {
    try
    {
      new Person().setPermanentAddress(null);
      //Lambdas not supported before Java 8, so roundabout method had to be used
      //If the previous code does not throw, this line fails the test
      Assertions.assertEquals(false, true);
    }
    catch (NullPointerException e)
    {
      //If code throws exception, test jumps here and passes
    }
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
  public void permanent_address_can_be_set()
  {
    personTest.setPermanentAddress(new Address()
            .setAddress("new address"));

    Assertions.assertEquals(new Address()
            .setAddress("new address"), personTest.getPermanentAddress());
  }

  @Test
  public void permanent_cannot_be_set_to_null()
  {
    try
    {
      personTest.setPermanentAddress(null);

      //Test fails
      Assertions.assertEquals(true, false);
    }
    catch (NullPointerException e)
    {
      //Test succeeds
    }
  }

  public void temporary_address_can_be_set()
  {
    personTest.setTemporaryAddress(new Address()
            .setAddress("new temporary address"));

    Assertions.assertEquals(new Address()
            .setAddress("new temporary address"), personTest.getTemporaryAddress());
  }
}
