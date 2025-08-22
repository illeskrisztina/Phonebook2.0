package application.main.model.database.interfaces;

import application.main.model.entity.ContactInfo;

import java.util.List;

public interface IContactInfoDAO
{
  public ContactInfo createContactInfo(ContactInfo contactInfo, int addressId);
  public ContactInfo getContactInfo(String contact);
  public List<ContactInfo> getAllContactInfo();
  public List<ContactInfo> getAllContactInfoForAddress(int addressId);
  public ContactInfo deleteContactInfo(String contact, int addressId);
}
