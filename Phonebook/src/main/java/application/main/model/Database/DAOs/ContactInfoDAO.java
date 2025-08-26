package application.main.model.Database.DAOs;

import application.main.model.Database.DatabaseHandlerFactory;
import application.main.model.Database.Interfaces.IContactInfoDAO;
import application.main.model.Entity.ContactInfo;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.*;
import java.util.ArrayList;

public class ContactInfoDAO extends DatabaseHandlerFactory implements IContactInfoDAO
{
  private static ContactInfoDAO instance;

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
      throw new RuntimeException("Issue getting singleton instance of ContactInfoDAO: " + e.getMessage());
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
      throw new RuntimeException("Something went wrong while creating a contact info in the database: " + e.getMessage());
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
        fetched = new ContactInfo().setContact(rs.getString("contact")).setType(rs.getString("type"));
      }

      return fetched;
    }
    catch (SQLException e)
    {
      throw new RuntimeException("Something went wrong while getting a contact info from the database: " + e.getMessage());
    }
  }

  @Override public ArrayList<ContactInfo> getAllContactInfo()
  {
    try(Connection connection = super.establishConnection())
    {
      ArrayList<ContactInfo> allContacts = new ArrayList<>();

      PreparedStatement statement = connection.prepareStatement("select contact_info.contact, contact_info.type from phonebook.contact_info;");
      ResultSet rs = statement.executeQuery();

      while (rs.next())
      {
        allContacts.add(new ContactInfo().setType(rs.getString("type")).setContact(rs.getString("contact")));
      }

      return allContacts;
    }
    catch (SQLException e)
    {
      throw new RuntimeException("Something went wrong while getting all contact information from the database: " + e.getMessage());
    }
  }

  public ArrayList<ContactInfo> getAllContactInfoForAddress(int addressId)
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
        allContacts.add(getContactInfo(rs.getString("contact")));
      }

      return allContacts;
    }
    catch (SQLException e)
    {
      throw new RuntimeException("Something went wrong while getting all contact information for address with id " + addressId + ": " + e.getMessage());
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
      throw new RuntimeException("Something went wrong while deleting a contact information from the database: " + e.getMessage());
    }
  }
}
