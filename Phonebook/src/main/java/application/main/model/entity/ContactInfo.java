package application.main.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "contact_info", schema = "phonebook")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class ContactInfo {
    @Id
    @Column(name = "contact")
    private String contact;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "contacts")
    private List<Address> addresses;
}
