package application.main.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "contact")
@Entity
@Table(name = "contact_info", schema = "phonebook")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class ContactInfo implements Serializable {
    @Id
    @Column(name = "contact")
    private String contact;

    @Column(name = "type")
    private String type;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "address_contacts", schema = "phonebook",
            joinColumns = {
                    @JoinColumn(name = "contact", referencedColumnName = "contact")},
            inverseJoinColumns = {
                    @JoinColumn(name = "address_id", referencedColumnName = "id")})
    private List<Address> addresses = new ArrayList<>();
}
