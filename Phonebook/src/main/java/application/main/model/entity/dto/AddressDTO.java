package application.main.model.entity.dto;

import application.main.model.entity.ContactInfo;
import application.main.model.entity.enums.AddressType;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddressDTO {
    private int id;
    private String residence;
    private AddressType type;
    private List<ContactInfo> contacts = new ArrayList<>();

    public void addContact(ContactInfo contact) {
        contacts.add(contact);
    }

    public ContactInfo removeContact(ContactInfo contact) {
        return contacts.remove(contacts.indexOf(contact));
    }
}
