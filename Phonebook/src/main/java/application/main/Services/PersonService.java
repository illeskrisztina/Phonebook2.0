package application.main.Services;

import application.main.Database.DAOs.AddressDAO;
import application.main.Database.DAOs.ContactInfoDAO;
import application.main.Database.DAOs.PersonDAO;
import application.main.Entities.Address;
import application.main.Entities.DTOs.SimplePersonDTO;
import application.main.Entities.Person;
import application.main.Services.Interfaces.IPersonService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PersonService implements IPersonService {
    private PersonDAO personDAO = PersonDAO.getInstance();
    private AddressDAO addressDAO = AddressDAO.getInstance();
    private ContactInfoDAO contactInfoDAO = ContactInfoDAO.getInstance();

    @Override
    public Person createPerson(Person person) {
        return personDAO.createPerson(person);
    }

    @Override
    public SimplePersonDTO getPerson(int Id) {
        SimplePersonDTO person = personDAO.getPerson(Id);

        if (person == null) {
            throw new NoSuchElementException("The person under id " + Id + " does not exist");
        }

        return person;
    }

    @Override
    public List<SimplePersonDTO> getAllPeople() {
        return personDAO.getAllPeople();
    }

    @Override
    public Person updatePerson(Person person) {
        Person updated = personDAO.updatePerson(person);

        if (updated == null) {
            throw new NoSuchElementException("The person under id " + person.getId() + " does not exist");
        }

        return updated;
    }

    @Override
    public Person deletePerson(int Id) {

        Person deleted = new Person();

        //Deleting a person means addresses and contacts need to be deleted too
        List<Address> addresses = addressDAO.getAllAddressesForPerson(Id).stream().map(address -> {
            contactInfoDAO.getAllContactInfoForAddress(address.getAddressId()).stream().forEach(contact ->
            {
                //Delete contacts
                contactInfoDAO.deleteContactInfo(contact.getContact(), address.getAddressId());
                address.addContact(contact);
            });

            switch (address.getType()){
                case "permanent":
                    deleted.setPermanentAddress(address);
                    break;
                case "temporary":
                    deleted.setTemporaryAddress(address);
                    break;
                default:
                    throw new NoSuchElementException("The address type " + address.getType() + " does not exist");
            }

            addressDAO.deleteAddress(address.getAddressId());
            return address;
        }).collect(Collectors.toList());

        //Lastly, delete the person
        SimplePersonDTO person = personDAO.deletePerson(Id);

        deleted.setId(person.getId());
        deleted.setName(person.getName());
        deleted.setAge(person.getAge());

        return deleted;
    }
}
