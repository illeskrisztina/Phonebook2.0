package application.main.Service.Interfaces;

import application.main.Model.Entity.Address;

import java.util.List;

public interface IAddressService {
    public Address createAddress(int personId, Address address);

    public Address getAddress(int id);

    public List<Address> getAllAddress(Integer personId);

    public Address updateAddress(Address address);

    public Address deleteAddress(int id);
}
