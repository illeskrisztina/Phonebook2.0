package application.main.service.interfaces;

import application.main.model.Entity.ContactInfo;

import java.util.List;

public interface IContactService {
    public ContactInfo addContact(ContactInfo contact, int addressId);

    public ContactInfo getContact(String contact);

    public List<ContactInfo> getAllContacts(Integer addressId);

    public ContactInfo deleteContact(String contact, int addressId);
}
