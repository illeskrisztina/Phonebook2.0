package application.main.service;

import application.main.model.database.dao.ContactInfoDAO;
import application.main.model.entity.ContactInfo;
import application.main.service.interfaces.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {
    private final ContactInfoDAO contactInfoDAO;

    @Override
    public ContactInfo addContact(ContactInfo contact) {
        return contactInfoDAO.save(contact);
    }

    @Override
    public ContactInfo getContact(String contact) {
        return contactInfoDAO.getReferenceById(contact);
    }

    @Override
    public List<ContactInfo> getAllContacts(Integer addressId) {
        if (addressId == null) {
            return contactInfoDAO.findAll();
        }
        else {
            return contactInfoDAO.findAll().stream()
                    .filter(contactInfo -> contactInfo.getAddresses().stream()
                            .anyMatch(address -> address.getId() == addressId))
                    .toList();
        }
    }

    @Override
    public ContactInfo deleteContact(String contact) {
        ContactInfo deleted =  getContact(contact);
        if(deleted != null) {
            contactInfoDAO.deleteById(contact);
            return deleted;
        }

        return null;
    }
}
