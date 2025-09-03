package application.main.model.database.interfaces;

import application.main.model.entity.ContactInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IContactInfoDAO
{
  public ContactInfo createContactInfo(ContactInfo contactInfo);
  public void addContactInfoToAddress(String contactInfo, int addressId);
  public ContactInfo getContactInfo(String contact);
  public List<ContactInfo> getAllContactInfo();
  public List<ContactInfo> getAllContactInfoForAddress(int addressId);
  public ContactInfo deleteContactInfo(String contact, int addressId);
}
