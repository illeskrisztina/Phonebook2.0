package application.main.model.entity.dto;

import application.main.model.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    SimplePersonDTO personToSimplePersonDTO(Person person);
    Person simplePersonDTOToPerson(SimplePersonDTO simplePersonDTO);
}
