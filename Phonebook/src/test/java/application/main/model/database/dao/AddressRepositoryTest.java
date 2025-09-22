package application.main.model.database.dao;

import application.main.model.entity.Address;
import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class AddressRepositoryTest {
    private Address addressTest;
    @Autowired
    private AddressDAO repo;

    @BeforeEach
    void setup() {
        addressTest = new Address()
                .setResidence("some address")
                .setType(AddressType.PERMANENT);
    }

    @Test
    void creating_address_returns_created_address() {
        int addressId = repo.save(addressTest).getId();

        Address saved = repo.findById(addressId).orElse(null);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals(addressTest.getResidence(), saved.getResidence());
        Assertions.assertEquals(addressTest.getType(), saved.getType());
    }

    @Test
    void saved_address_can_be_retrieved_by_id() {
        Address retrieved = repo.findById(1).orElse(null);

        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals("Hungary, Budapest, 1194, Some street 13.", retrieved.getResidence());
        Assertions.assertEquals("permanent", retrieved.getType());
    }

    @Test
    void getting_non_existent_address_returns_null() {
        Address retrieved = repo.findById(10).orElse(null);

        Assertions.assertNull(retrieved);
    }

    @Test
    void all_addresses_can_be_retrieved_from_database() {
        List<Address> addresses = repo.findAll();

        Assertions.assertEquals(4,  addresses.size());
    }

    @Test
    void updating_address_saves_it_in_database() {
        int addressId = repo.save(addressTest).getId();
        addressTest.setResidence("other address of some address");
        addressTest.setType(AddressType.TEMPORARY);

        repo.save(addressTest);

        Address modified = repo.findById(addressId).orElse(null);
        Assertions.assertNotNull(modified);
        Assertions.assertEquals("other address of some address",  modified.getResidence());
        Assertions.assertEquals(AddressType.TEMPORARY, modified.getType());
    }

    @Test
    void deleting_address_removes_it_from_database() {
        repo.deleteById(3);

        Address deleted = repo.findById(3).orElse(null);
        Assertions.assertNull(deleted);
    }
}
