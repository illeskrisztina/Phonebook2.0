package application.main.model.entity.dto;

import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
public class ContactInfoDTO {
    private String contact;
    private String type;
}
