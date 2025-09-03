package application.main.service.interfaces;

import application.main.model.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAddressService {
    public Address createAddress(int personId, Address address);

    public Address getAddress(int id);

    public List<Address> getAllAddress(Integer personId);

    public Address updateAddress(Address address);

    public Address deleteAddress(int id);
}
