package application.main.service;

import application.main.model.Database.DAOs.ContactInfoDAO;
import application.main.model.Database.Interfaces.IContactInfoDAO;
import application.main.model.entity.ContactInfo;
import application.main.service.interfaces.IContactService;

import java.util.List;

public class ContactService implements IContactService {
    private final IContactInfoDAO contactInfoDAO = ContactInfoDAO.getInstance();

    @Override
    public ContactInfo addContact(ContactInfo contact, int addressId) {
        return contactInfoDAO.createContactInfo(contact, addressId);
    }

    @Override
    public ContactInfo getContact(String contact) {
        return contactInfoDAO.getContactInfo(contact);
    }

    @Override
    public List<ContactInfo> getAllContacts(Integer addressId) {
        if (addressId == null) {
            return contactInfoDAO.getAllContactInfo();
        }
        else {
            return contactInfoDAO.getAllContactInfoForAddress(addressId);
        }
    }

    @Override
    public ContactInfo deleteContact(String contact, int addressId) {
        return contactInfoDAO.deleteContactInfo(contact, addressId);
    }
}
