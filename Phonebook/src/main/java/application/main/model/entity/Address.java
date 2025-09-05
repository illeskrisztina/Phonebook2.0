package application.main.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Entity
@Table(name = "address", schema = "phonebook")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Address {

    @Column(name = "address")
    private String residence;
    @Column(name = "type")
    private String type;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int addressId;
    @OneToMany
    private ArrayList<ContactInfo> contacts = new ArrayList<>();
    @Column(name = "person_id")
    @OneToOne
    private int personId;


    public void addContact(ContactInfo contact) {
        contacts.add(contact);
    }

    public ContactInfo removeContact(ContactInfo contact) {
        return contacts.remove(contacts.indexOf(contact));
    }
}
