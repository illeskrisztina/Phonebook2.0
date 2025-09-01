package application.main.model.database.interfaces;

import application.main.model.entity.Address;

import java.util.List;

public interface IAddressDAO
{
  public Address createAddress(Address address, int personId);
  public Address getAddress(int id);
  public List<Address> getAllAddresses();
  public List<Address> getAllAddressesForPerson(int personId);
  public Address updateAddress(Address address);
  public Address deleteAddress(int addressId);
}
