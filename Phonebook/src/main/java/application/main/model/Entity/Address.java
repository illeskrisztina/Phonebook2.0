package application.main.model.Entity;

import java.util.ArrayList;

public class Address
{
  private String address;
  private String type;
  private int addressId;
  private ArrayList<ContactInfo> contacts = new ArrayList<>();

  public Address()
  {
  }

  public Address(String address)
  {
    this.address = address;
    this.contacts = new ArrayList<>();
  }

  public Address(String address, String type, int addressId)
  {
    this.address = address;
    this.type = type;
    this.addressId = addressId;
    this.contacts = new ArrayList<>();
  }

  public Address setAddress(String address)
  {
    this.address = address;
    return this;
  }

  public Address setType(String type)
  {
    this.type = type;
    return this;
  }

  public Address setAddressId(int addressId)
  {
    this.addressId = addressId;
    return this;
  }

  public Address setContacts(ArrayList<ContactInfo> contacts)
  {
    this.contacts = new ArrayList<>(contacts);
    return this;
  }

  public void addContact(ContactInfo contact)
  {
    contacts.add(contact);
  }

  public String getAddress()
  {
    return address;
  }

  public ArrayList<ContactInfo> getContacts()
  {
    return new ArrayList<>(contacts);
  }

  public String getType()
  {
    return type;
  }

  public int getAddressId()
  {
    return addressId;
  }

  public ContactInfo removeContact(ContactInfo contact)
  {
    return contacts.remove(contacts.indexOf(contact));
  }

  public boolean equals(Object obj)
  {
    if(obj == this)
    {
      return true;
    }
    if(obj == null || obj.getClass() != this.getClass())
    {
      return false;
    }

    Address other = (Address) obj;
    return other.address.equals(this.address) && other.contacts.containsAll(this.contacts);
  }

  public String toString()
  {
    String contactList = "";

    for (int i = 0; i < contacts.size(); i++)
    {
      contactList = contactList.concat(contacts.get(i) + "\n");
    }

    return "Address: " + address + "\nList of Contacts:\n";
  }
}
