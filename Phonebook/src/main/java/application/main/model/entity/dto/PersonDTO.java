package application.main.model.entity.dto;

import application.main.model.entity.Address;
import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Accessors(chain=true)
public class PersonDTO {
    private Integer id;
    private String name;
    private int age;
    private Address permanentAddress;
    private Address temporaryAddress;
}
