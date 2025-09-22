package application.main.model.database.dao;

import application.main.model.entity.ContactInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class ContactInfoRepositoryTest {
    @Autowired
    private ContactInfoDAO repo;
    private ContactInfo contactInfoTest;

    @BeforeEach
    void setup() {
        contactInfoTest = new ContactInfo()
                .setContact("somethingsomething@gmail.com")
                .setType("email");
    }

    @Test
    void saving_contact_saves_it_in_database()
    {
        repo.save(contactInfoTest);

        ContactInfo saved = repo.findById(contactInfoTest.getContact()).orElse(null);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals(contactInfoTest.getContact(), saved.getContact());
        Assertions.assertEquals(contactInfoTest.getType(), saved.getType());
    }

    @Test
    void getting_contact_info_returns_correct_entity_from_database()
    {
        ContactInfo saved = repo.findById("+36 30 555 6666").orElse(null);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals("+36 30 555 6666", saved.getContact());
        Assertions.assertEquals("mobile phone", saved.getType());
    }

    @Test
    void getting_non_existent_contact_info_returns_null()
    {
        Assertions.assertNull(repo.findById(contactInfoTest.getContact()).orElse(null));
    }

    @Test
    void getting_all_contacts_from_database_returns_all_contacts_saved()
    {
        List<ContactInfo> saved = repo.findAll();

        Assertions.assertEquals(3, saved.size());
    }

    @Test
    void deleting_contact_info_deletes_it_from_database()
    {
        repo.deleteById(contactInfoTest.getContact());

        ContactInfo deleted = repo.findById(contactInfoTest.getContact()).orElse(null);

        Assertions.assertNull(deleted);
    }
}
