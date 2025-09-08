package application.main.service.interfaces;

import application.main.model.entity.ContactInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IContactService {
    public ContactInfo addContact(ContactInfo contact);

    public ContactInfo getContact(String contact);

    public List<ContactInfo> getAllContacts(Integer addressId);

    public ContactInfo deleteContact(String contact);
}
