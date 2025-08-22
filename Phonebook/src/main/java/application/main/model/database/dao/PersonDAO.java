package application.main.model.database.dao;

import application.main.model.database.DatabaseHandlerFactory;
import application.main.model.database.interfaces.IPersonDAO;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO extends DatabaseHandlerFactory implements IPersonDAO {
    private static PersonDAO instance;

    private PersonDAO() throws SQLException {
        DriverManager.registerDriver(new SQLServerDriver());
    }

    public static synchronized PersonDAO getInstance() {
        try {
            if (instance == null) {
                instance = new PersonDAO();
            }
            return instance;
        } catch (SQLException e) {
            throw new RuntimeException("Issue getting singleton instance of PersonDAO: " + e.getMessage());
        }
    }

    @Override
    public synchronized Person createPerson(Person person) {
        try (Connection connection = super.establishConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into phonebook.people(name, age) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                person.setId(generatedKeys.getInt(1));
            } else {
                throw new RuntimeException("No keys were generated.");
            }
            return person;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while creating an entry for a person in the database: " + e.getMessage());
        }
    }

    @Override
    public SimplePersonDTO getPerson(int Id) {
        try (Connection connection = super.establishConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT people.name, people.age, people.id\n"
                    + "FROM phonebook.people\n" + "WHERE people.id = ?;");
            statement.setInt(1, Id);
            ResultSet rs = statement.executeQuery();

            SimplePersonDTO fetched = null;
            while (rs.next()) {
                fetched = new SimplePersonDTO(rs.getString("name"),
                        rs.getInt("age"), rs.getInt("id"));
            }

            return fetched;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while fetching the data of the person with id " + Id + ": " + e.getMessage());
        }
    }

    @Override
    public List<SimplePersonDTO> getAllPeople() {
        try (Connection connection = super.establishConnection()) {
            List<SimplePersonDTO> allPeople = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement("SELECT people.name, people.age, people.id\n"
                    + "FROM phonebook.people;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int Id = rs.getInt("id");
                allPeople.add(new SimplePersonDTO(name, age, Id));
            }

            return allPeople;
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database while retrieving information for people stored in the database: " + e.getMessage());
        }
    }

    @Override
    public synchronized Person updatePerson(Person person) {
        try (Connection connection = super.establishConnection()) {
            PreparedStatement statement = connection.prepareStatement("update phonebook.people set name = ?, age = ? where id = ?;");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setInt(3, person.getId());
            statement.executeUpdate();

            return person;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while trying to update the information of a person with id " + person.getId() + ": " + e.getMessage());
        }
    }

    @Override
    public synchronized Person deletePerson(int Id) {
        try (Connection connection = super.establishConnection()) {
            SimplePersonDTO deleted = getPerson(Id);
            PreparedStatement statement = connection.prepareStatement("delete from phonebook.people where id = ?;");
            statement.setInt(1, Id);
            statement.executeUpdate();

            return new Person(deleted);
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while deleting person with id " + Id + " from the database: " + e.getMessage());
        }
    }
}
