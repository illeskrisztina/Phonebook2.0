import application.main.model.entity.ContactInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactInfoTest
{
  ContactInfo contactTest;
  @BeforeEach
  public void setUp()
  {
    contactTest = new ContactInfo("mobile phone", "+36 1 234 5678");
  }

  @Test
  public void created_object_not_null()
  {
    Assertions.assertNotNull(contactTest);
  }

  @Test
  public void created_object_has_correct_contact_type()
  {
    Assertions.assertEquals("mobile phone", contactTest.getType());
  }

  @Test
  public void created_object_has_correct_contact()
  {
    Assertions.assertEquals("+36 1 234 5678", contactTest.getContact());
  }

  @Test
  public void created_object_type_can_be_set()
  {
    contactTest.setType("email");

    Assertions.assertEquals("email", contactTest.getType());
  }

  @Test
  public void created_object_contact_can_be_set()
  {
    contactTest.setContact("hi@gmail.com");

    Assertions.assertEquals("hi@gmail.com", contactTest.getContact());
  }

  @Test
  public void different_objects_same_attributes_are_equal()
  {
    ContactInfo other = new ContactInfo("mobile phone", "+36 1 234 5678");

    Assertions.assertEquals(true, other.equals(contactTest));
  }

  @Test
  public void different_objects_different_attributes_not_equal()
  {
    ContactInfo other = new ContactInfo("hi", "hi@gmail.com");

    Assertions.assertEquals(false, other.equals(contactTest));
  }
}
