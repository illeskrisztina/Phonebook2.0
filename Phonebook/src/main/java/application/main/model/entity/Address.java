package application.main.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Address
{
  private String residence;
  private String type;
  private int addressId;
  private ArrayList<ContactInfo> contacts = new ArrayList<>();

  public Address()
  {
  }

  public Address(String residence)
  {
    this.residence = residence;
    this.contacts = new ArrayList<>();
  }

  public Address(String residence, String type, int addressId)
  {
    this.residence = residence;
    this.type = type;
    this.addressId = addressId;
    this.contacts = new ArrayList<>();
  }

  public Address setResidence(String residence)
  {
    this.residence = residence;
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

  public Address setContacts(List<ContactInfo> contacts)
  {
    this.contacts = new ArrayList<>(contacts);
    return this;
  }

  public void addContact(ContactInfo contact)
  {
    contacts.add(contact);
  }

  public String getResidence()
  {
    return residence;
  }

  public List<ContactInfo> getContacts()
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

    @Override
    public int hashCode() {
        return Objects.hash(residence, type, addressId, contacts);
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
    return other.residence.equals(this.residence) && other.contacts.containsAll(this.contacts);
  }

  public String toString()
  {
    String contactList = "";

    for (int i = 0; i < contacts.size(); i++)
    {
      contactList = contactList.concat(contacts.get(i) + "\n");
    }

    return "Address: " + residence + "\nList of Contacts:\n";
  }
}
