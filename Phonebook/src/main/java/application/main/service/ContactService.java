package application.main.service;

import application.main.model.database.dao.ContactInfoDAO;
import application.main.model.entity.ContactInfo;
import application.main.service.interfaces.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        return contactInfoDAO.findById(contact)
                .orElseThrow(() -> new NoSuchElementException("Contact " + contact + " does not exist."));
    }

    @Override
    public List<ContactInfo> getAllContacts() {
        return contactInfoDAO.findAll();
    }

    @Override
    public void deleteContact(String contact) {
        if(contactInfoDAO.existsById(contact)) {
            contactInfoDAO.deleteById(contact);
        }
    }
}
