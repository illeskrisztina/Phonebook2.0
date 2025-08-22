package application.main.controller;

import application.main.model.entity.ContactInfo;
import application.main.service.Dispatcher;
import application.main.service.Interfaces.IDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ContactInfoController {
    private final IDispatcher dispatcher = new Dispatcher();

    @PostMapping("/person/{personId}/contact")
    public ResponseEntity<ContactInfo> addContactInfo(@PathVariable int personId, @RequestBody ContactInfo contactInfo) {
        ContactInfo created = dispatcher.addContact(contactInfo, personId);
        if(created == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<ContactInfo> getContactInfo(@PathVariable String contactId) {
        ContactInfo fetched = dispatcher.getContact(contactId);
        if(fetched == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetched, HttpStatus.OK);
    }

    @GetMapping("/contact")
    public ResponseEntity<List<ContactInfo>> getAllContactInfo(@RequestParam(name = "addressId", required = false) Integer addressId) {
        return new ResponseEntity<>(dispatcher.getAllContacts(addressId),  HttpStatus.OK);
    }

    @DeleteMapping("/address/{addressId}/contact/{contactId}")
    public ResponseEntity<ContactInfo> deleteContactInfo(@PathVariable int addressId, @PathVariable String contactId) {
        ContactInfo deleted = dispatcher.deleteContact(contactId, addressId);
        if(deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
