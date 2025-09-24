package application.main.service;

import application.main.model.database.dao.AddressDAO;
import application.main.model.entity.Address;
import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressDAO repo;
    @InjectMocks
    private AddressService service;

    private Address addressTest;

    @BeforeEach
    void setUp() {
        addressTest = new Address()
                .setResidence("some place in Hungary")
                .setType(AddressType.PERMANENT);
    }

    @Test
    void createAddress_calls_appropriate_repository_method() {
        when(repo.save(addressTest)).thenReturn(addressTest.setId(1));

        service.createAddress(addressTest);

        verify(repo, times(1)).save(addressTest);
    }

    @Test
    void getAddress_calls_appropriate_repository_method() {
        when(repo.findById(1)).thenReturn(Optional.of(addressTest));

        service.getAddress(1);

        verify(repo, times(1)).findById(1);
    }

    @Test
    void getAddress_throws_exception_if_repository_returns_null() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> service.getAddress(1));
    }

    @Test
    void getAllAddresses_calls_appropriate_repository_method() {
        when(repo.findAll()).thenReturn(List.of(addressTest));

        service.getAllAddress();

        verify(repo, times(1)).findAll();
    }

    @Test
    void updateAddress_calls_appropriate_repository_method() {
        when(repo.save(addressTest)).thenReturn(addressTest);

        service.updateAddress(addressTest);

        verify(repo, times(1)).save(addressTest);
    }

    @Test
    void deleteAddress_calls_appropriate_repository_method() {
        when(repo.existsById(1)).thenReturn(true);

        service.deleteAddress(1);

        verify(repo, times(1)).existsById(1);
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void deleteAddress_does_not_call_delete_method_if_entity_does_not_exist() {
        lenient().when(repo.existsById(1)).thenReturn(false);

        service.deleteAddress(1);

        verify(repo, never()).deleteById(1);
    }
}
