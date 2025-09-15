package application.main;

import application.main.model.entity.ContactInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ContactInfoTest {
    ContactInfo contactTest;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        contactTest = new ContactInfo()
                .setType("mobile phone")
                .setContact("+36 1 234 5678");
    }

    @Test
    void created_object_not_null() {
        Assertions.assertNotNull(contactTest);
    }

    @Test
    void created_object_has_correct_contact_type() {
        Assertions.assertEquals("mobile phone", contactTest.getType());
    }

    @Test
    void created_object_has_correct_contact() {
        Assertions.assertEquals("+36 1 234 5678", contactTest.getContact());
    }

    @Test
    void created_object_type_can_be_set() {
        contactTest.setType("email");

        Assertions.assertEquals("email", contactTest.getType());
    }

    @Test
    void created_object_contact_can_be_set() {
        contactTest.setContact("hi@gmail.com");

        Assertions.assertEquals("hi@gmail.com", contactTest.getContact());
    }

    @Test
    void different_objects_same_attributes_are_equal() {
        ContactInfo other = new ContactInfo()
                .setType("mobile phone")
                .setContact("+36 1 234 5678");

        Assertions.assertEquals(true, other.equals(contactTest));
    }

    @Test
    void different_objects_different_attributes_not_equal() {
        ContactInfo other = new ContactInfo()
                .setType("email")
                .setContact("hi@gmail.com");

        Assertions.assertEquals(false, other.equals(contactTest));
    }
}
