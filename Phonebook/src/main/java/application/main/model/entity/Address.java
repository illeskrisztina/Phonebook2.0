package application.main.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address", schema = "phonebook")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "address")
    private String residence;

    @Column(name = "type")
    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_contacts", schema = "phonebook",
            joinColumns = {
            @JoinColumn(name = "address_id", referencedColumnName = "id")},
            inverseJoinColumns = {
            @JoinColumn(name = "contact", referencedColumnName = "contact")})
    private List<ContactInfo> contacts = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;


    public void addContact(ContactInfo contact) {
        contacts.add(contact);
    }

    public ContactInfo removeContact(ContactInfo contact) {
        return contacts.remove(contacts.indexOf(contact));
    }
}
