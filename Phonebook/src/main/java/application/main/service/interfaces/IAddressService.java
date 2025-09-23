package application.main.service.interfaces;

import application.main.model.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAddressService {
    public Address createAddress(Address address);

    public Address getAddress(int id);

    public List<Address> getAllAddress();

    public Address updateAddress(Address address);

    public void deleteAddress(int id);
}
