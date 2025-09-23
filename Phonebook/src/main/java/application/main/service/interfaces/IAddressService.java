package application.main.service.interfaces;

import application.main.model.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAddressService {
    Address createAddress(Address address);

    Address getAddress(int id);

    List<Address> getAllAddress();

    Address updateAddress(Address address);

    void deleteAddress(int id);
}
