package application.main.service;

import application.main.model.database.dao.ContactInfoDAO;
import application.main.model.database.interfaces.IContactInfoDAO;
import application.main.model.entity.ContactInfo;
import application.main.service.interfaces.IContactService;

import java.util.List;

public class ContactService implements IContactService {
    private final IContactInfoDAO contactInfoDAO = ContactInfoDAO.getInstance();

    @Override
    public ContactInfo addContact(ContactInfo contact, Integer addressId) {
        ContactInfo added = contactInfoDAO.createContactInfo(contact);

        if(addressId != null)
        {
            contactInfoDAO.addContactInfoToAddress(added.getContact(), addressId);
        }
        return added;
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
