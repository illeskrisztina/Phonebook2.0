package application.main;

import application.main.model.entity.Address;
import application.main.model.entity.Person;
import application.main.model.entity.dto.PersonMapper;
import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;


class PersonTest {
    private Person personTest;
    private Address temporaryAddress;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PersonMapper mapper;

    @BeforeEach
    void setUp() {
        personTest = new Person()
                .setAge(25)
                .setName("Melanie");
        temporaryAddress = new Address()
                .setType(AddressType.TEMPORARY)
                .setResidence("other address");
    }

    @Test
    void name_can_be_set() {
        personTest.setName("David");

        Assertions.assertEquals("David", personTest.getName());
    }

    @Test
    void age_can_be_set() {
        personTest.setAge(55);

        Assertions.assertEquals(55, personTest.getAge());
    }

    @Test
    void id_can_be_set() {
        personTest.setId(4);

        Assertions.assertEquals(4, personTest.getId());
    }

    @Test
    void temporary_address_can_be_set() {
        personTest.setTemporaryAddress(new Address()
                .setType(AddressType.TEMPORARY)
                .setResidence("other address"));

        Assertions.assertEquals(temporaryAddress, personTest.getTemporaryAddress());
    }
}
