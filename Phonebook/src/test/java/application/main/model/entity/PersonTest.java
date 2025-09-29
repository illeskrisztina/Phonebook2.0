package application.main.model.entity;

import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PersonTest {
    private Person personTest;
    private Address temporaryAddress;

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
