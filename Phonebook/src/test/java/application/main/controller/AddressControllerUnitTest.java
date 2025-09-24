package application.main.controller;

import application.main.model.entity.Address;
import application.main.model.entity.dto.AddressDTO;
import application.main.model.entity.enums.AddressType;
import application.main.service.interfaces.IDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressControllerUnitTest {
    @Mock
    private IDispatcher dispatcher;
    @InjectMocks
    private AddressController controller;
    private Address temporaryAddressTest;

    @BeforeEach
    void setUp() {
        temporaryAddressTest = new Address()
                .setResidence("some address")
                .setType(AddressType.TEMPORARY);
    }

    @Test
    void when_creating_entity_if_entity_not_created_bad_request_status_code_returned() {
        when(dispatcher.createAddress(any(Integer.class), any(Address.class))).thenReturn(null);

        ResponseEntity<AddressDTO> created = controller.addAddress(1, temporaryAddressTest);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, created.getStatusCode());
    }

    @Test
    void when_updating_address_entity_if_entity_returns_null_not_found_status_code_is_returned() {
        when(dispatcher.updateAddress(any(Address.class))).thenReturn(null);

        ResponseEntity<AddressDTO> updated = controller.updateAddress(temporaryAddressTest);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, updated.getStatusCode());
    }
}
