package application.main.service;

import application.main.model.database.dao.AddressDAO;
import application.main.model.entity.Address;
import application.main.service.interfaces.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final AddressDAO addressDAO = AddressDAO.getInstance();

    @Override
    public Address createAddress(int personId, Address address) {
        return addressDAO.createAddress(address, personId);
    }

    @Override
    public Address getAddress(int id) {
        return addressDAO.getAddress(id);
    }

    @Override
    public List<Address> getAllAddress(Integer personId) {
        if(personId == null)
        {
            return addressDAO.getAllAddresses();
        }
        else
        {
            return addressDAO.getAllAddressesForPerson(personId);
        }
    }

    @Override
    public Address updateAddress(Address address) {
        return addressDAO.updateAddress(address);
    }

    @Override
    public Address deleteAddress(int id) {
        return addressDAO.deleteAddress(id);
    }
}
