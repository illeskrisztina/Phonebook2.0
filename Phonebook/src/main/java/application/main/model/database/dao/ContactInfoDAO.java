package application.main.model.database.dao;

import application.main.model.database.DatabaseHandlerFactory;
import application.main.model.database.interfaces.IContactInfoDAO;
import application.main.model.entity.ContactInfo;
import application.main.model.exception.DatabaseConnectionException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactInfoDAO extends DatabaseHandlerFactory implements IContactInfoDAO
{
  private static ContactInfoDAO instance;

  private static final String CONTACT = "contact";

  private ContactInfoDAO() throws SQLException
  {
    DriverManager.registerDriver(new SQLServerDriver());
  }

  public static synchronized ContactInfoDAO getInstance()
  {
    try
    {
      if (instance == null)
      {
        instance = new ContactInfoDAO();
      }
      return instance;
    }
    catch (SQLException e)
    {
      throw new DatabaseConnectionException("Issue getting singleton instance of ContactInfoDAO: " + e.getMessage());
    }
  }
  @Override public synchronized ContactInfo createContactInfo(ContactInfo contactInfo, int addressId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("insert into phonebook.contact_info(type, contact) values (?, ?);");
      statement.setString(1, contactInfo.getType());
      statement.setString(2, contactInfo.getContact());
      statement.executeUpdate();

      statement = connection.prepareStatement("insert into phonebook.address_contacts(contact, address_id) values (?, ?);");
      statement.setString(1, contactInfo.getContact());
      statement.setInt(2, addressId);
      statement.executeUpdate();

      return contactInfo;
    }
    catch (SQLException e)
    {
      throw new DatabaseConnectionException("Something went wrong while creating a contact info in the database: " + e.getMessage());
    }
  }

  @Override public ContactInfo getContactInfo(String contact)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("select contact_info.contact, contact_info.type from phonebook.contact_info where contact_info.contact = ?;");
      statement.setString(1, contact);
      ResultSet rs = statement.executeQuery();

      ContactInfo fetched = null;
      while (rs.next())
      {
        fetched = new ContactInfo(rs.getString("type"),
            rs.getString(CONTACT));
      }

      return fetched;
    }
    catch (SQLException e)
    {
      throw new DatabaseConnectionException("Something went wrong while getting a contact info from the database: " + e.getMessage());
    }
  }

  @Override public List<ContactInfo> getAllContactInfo()
  {
    try(Connection connection = super.establishConnection())
    {
      ArrayList<ContactInfo> allContacts = new ArrayList<>();

      PreparedStatement statement = connection.prepareStatement("select contact_info.contact, contact_info.type from phonebook.contact_info;");
      ResultSet rs = statement.executeQuery();

      while (rs.next())
      {
        allContacts.add(new ContactInfo(rs.getString("type"), rs.getString(CONTACT)));
      }

      return allContacts;
    }
    catch (SQLException e)
    {
      throw new DatabaseConnectionException("Something went wrong while getting all contact information from the database: " + e.getMessage());
    }
  }

  public List<ContactInfo> getAllContactInfoForAddress(int addressId)
  {
    try(Connection connection = super.establishConnection())
    {
      ArrayList<ContactInfo> allContacts = new ArrayList<>();

      PreparedStatement statement = connection.prepareStatement("select contact\n"
          + "from phonebook.address_contacts where address_id = ?;");
      statement.setInt(1, addressId);
      ResultSet rs = statement.executeQuery();

      while (rs.next())
      {
        allContacts.add(getContactInfo(rs.getString(CONTACT)));
      }

      return allContacts;
    }
    catch (SQLException e)
    {
      throw new DatabaseConnectionException("Something went wrong while getting all contact information for address with id " + addressId + ": " + e.getMessage());
    }
  }

  @Override public synchronized ContactInfo deleteContactInfo(String contact, int addressId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("delete from phonebook.address_contacts where address_id = ? and contact = ?;");
      statement.setInt(1, addressId);
      statement.setString(2, contact);
      statement.executeUpdate();

      ContactInfo deleted = getContactInfo(contact);

      statement = connection.prepareStatement("delete from phonebook.contact_info where contact = ?;");
      statement.setString(1, contact);
      statement.executeUpdate();

      return deleted;
    }
    catch (SQLException e)
    {
      throw new DatabaseConnectionException("Something went wrong while deleting a contact information from the database: " + e.getMessage());
    }
  }
}
