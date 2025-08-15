package application.main.service;

import application.main.model.Database.DAOs.AddressDAO;
import application.main.model.Entity.Address;
import application.main.service.Interfaces.IAddressService;

import java.util.List;

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
