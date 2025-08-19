import application.main.Entities.Address;
import application.main.Entities.ContactInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AddressTest {
    private Address addressTest;

    @BeforeEach
    public void setUp() {
        addressTest = new Address().setAddress("Hungary, Budapest, XIX. district");
    }

    @Test
    public void created_object_has_correct_address() {
        Assertions.assertEquals("Hungary, Budapest, XIX. district", addressTest.getAddress());
    }

    @Test
    public void created_object_has_empty_list() {
        Assertions.assertEquals(0, addressTest.getContacts().size());
    }

    @Test
    public void adding_contact_increases_list_size_by_one() {
        Assertions.assertEquals(0, addressTest.getContacts().size());
        ContactInfo contact = new ContactInfo().setType("hi").setContact("hello");
        addressTest.addContact(contact);

        Assertions.assertEquals(1, addressTest.getContacts().size());
    }

    @Test
    public void address_can_be_set() {
        addressTest.setAddress("new address");

        Assertions.assertEquals("new address", addressTest.getAddress());
    }

    @Test
    public void contact_list_can_be_set() {
        ArrayList<ContactInfo> newList = new ArrayList<>();
        newList.add(new ContactInfo().setType("hi").setContact("hello"));
        newList.add(new ContactInfo().setType("hoi").setContact("heio"));
        newList.add(new ContactInfo().setType("morning").setContact("good morning"));

        addressTest.setContacts(newList);

        Assertions.assertEquals(3, addressTest.getContacts().size());
        Assertions.assertEquals(newList, addressTest.getContacts());
    }

    @Test
    public void removing_contact_removes_right_contact() {
        ContactInfo contact1 = new ContactInfo().setType("hi").setContact("hello");
        ContactInfo contact2 = new ContactInfo().setType("hoi").setContact("heio");
        ContactInfo contact3 = new ContactInfo().setType("morning").setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        //New one to ensure it doesn't just check for reference variables
        ContactInfo removed = addressTest.removeContact(new ContactInfo().setType("hoi").setContact("heio"));

        Assertions.assertEquals(contact2, removed);
    }

    @Test
    public void removing_contact_decreases_size_of_list() {
        ContactInfo contact1 = new ContactInfo().setType("hi").setContact("hello");
        ContactInfo contact2 = new ContactInfo().setType("hoi").setContact("heio");
        ContactInfo contact3 = new ContactInfo().setType("morning").setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        addressTest.removeContact(new ContactInfo().setType("hoi").setContact("heio"));

        Assertions.assertEquals(2, addressTest.getContacts().size());
    }

    @Test
    public void removing_contact_leaves_right_ones_behind() {
        ContactInfo contact1 = new ContactInfo().setType("hi").setContact("hello");
        ContactInfo contact2 = new ContactInfo().setType("hoi").setContact("heio");
        ContactInfo contact3 = new ContactInfo().setType("morning").setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        addressTest.removeContact(new ContactInfo().setType("hoi").setContact("heio"));

        Assertions.assertEquals(contact1, addressTest.getContacts().get(0));
        Assertions.assertEquals(contact3, addressTest.getContacts().get(1));
    }

    @Test
    public void constructing_same_contacts_list_still_returns_equal() {
        ContactInfo contact1 = new ContactInfo().setType("hi").setContact("hello");
        ContactInfo contact2 = new ContactInfo().setType("hoi").setContact("heio");
        ContactInfo contact3 = new ContactInfo().setType("morning").setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        Address other = new Address().setAddress("Hungary, Budapest, XIX. district");

        other.addContact(new ContactInfo().setType("hi").setContact("hello"));
        other.addContact(new ContactInfo().setType("hoi").setContact("heio"));
        other.addContact(new ContactInfo().setType("morning").setContact("good morning"));

        Assertions.assertEquals(true, other.equals(addressTest));
    }

    @Test
    public void address_with_different_attributes_not_equal() {
        ContactInfo contact1 = new ContactInfo().setType("hi").setContact("hello");
        ContactInfo contact2 = new ContactInfo().setType("hoi").setContact("heio");
        ContactInfo contact3 = new ContactInfo().setType("morning").setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        Address other = new Address().setAddress("some address");

        other.addContact(new ContactInfo().setType("hi").setContact("hello"));

        Assertions.assertEquals(false, other.equals(addressTest));
    }
}
