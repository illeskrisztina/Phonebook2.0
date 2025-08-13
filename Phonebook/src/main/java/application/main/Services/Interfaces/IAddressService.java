package application.main.Services.Interfaces;

import application.main.Entities.Address;

import java.util.List;

public interface IAddressService {
    public Address createAddress(Address address);

    public Address getAddress(int id);

    public List<Address> getAllAddress();

    public Address updateAddress(Address address);

    public Address deleteAddress(int id);
}
