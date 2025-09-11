package application.main.service;

import application.main.model.database.dao.AddressDAO;
import application.main.model.entity.Address;
import application.main.service.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final AddressDAO addressDAO;

    @Override
    public Address createAddress(Address address) {
        return addressDAO.save(address);
    }

    @Override
    public Address getAddress(int id) {
        return addressDAO.findById(id).orElse(null);
    }

    @Override
    public List<Address> getAllAddress() {
        return addressDAO.findAll();
    }

    @Override
    public Address updateAddress(Address address) {
        return addressDAO.save(address);
    }

    @Override
    public Address deleteAddress(int id) {

        Address deleted = addressDAO.findById(id).orElse(null);
        if (deleted != null) {
            addressDAO.deleteById(id);
        }

        return deleted;
    }
}
