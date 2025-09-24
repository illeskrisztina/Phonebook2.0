package application.main.model.entity.dto;

import application.main.model.entity.ContactInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactInfoMapper {
    ContactInfoDTO contactInfoToContactInfoDTO(ContactInfo contactInfo);
    ContactInfo contactInfoDTOToContactInfo(ContactInfoDTO contactInfoDTO);
}
