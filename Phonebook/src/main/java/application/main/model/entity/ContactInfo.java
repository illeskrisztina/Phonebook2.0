package application.main.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

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

    @Column(name = "type")
    private String type;
    @Id
    @Column(name = "contact")
    private String contact;
}
