package application.main.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain=true)
public class SimplePersonDTO
{
  private String name;
  private int age;
  private int id;
}
