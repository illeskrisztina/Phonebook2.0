import application.main.Model.Entity.Address;
import application.main.Model.Entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest
{
  private Person personTest;

  @BeforeEach
  public void setUp()
  {
    personTest = new Person("Melanie", 25, 1, new Address("Denmark, Aarhus"));
  }

  @Test
  public void setting_permanent_to_null_throws_error()
  {
    try
    {
      new Person("Kate", 34, 2, null);

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
    personTest.setPermanentAddress(new Address("new address"));

    Assertions.assertEquals(new Address("new address"), personTest.getPermanentAddress());
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
    personTest.setTemporaryAddress(new Address("new temporary address"));

    Assertions.assertEquals(new Address("new temporary address"), personTest.getTemporaryAddress());
  }
}
