package application.main.model.database.dao;

import application.main.model.entity.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInfoDAO extends JpaRepository<ContactInfo, String>
{
}
