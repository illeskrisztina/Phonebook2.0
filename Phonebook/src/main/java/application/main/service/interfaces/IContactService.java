package application.main.service.interfaces;

import application.main.model.entity.ContactInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IContactService {
    ContactInfo addContact(ContactInfo contact);

    ContactInfo getContact(String contact);

    List<ContactInfo> getAllContacts();

    void deleteContact(String contact);
}
