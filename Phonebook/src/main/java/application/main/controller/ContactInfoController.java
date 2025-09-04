package application.main.controller;

import application.main.model.entity.ContactInfo;
import application.main.service.interfaces.IDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ContactInfoController {
    private final IDispatcher dispatcher;
    private static final String ERROR_HEADER = "Error";

    @PostMapping("/person/{personId}/contact")
    public ResponseEntity<ContactInfo> addContactInfo(@PathVariable int personId, @RequestBody ContactInfo contactInfo) {
        ContactInfo created = dispatcher.addContact(contactInfo, personId);
        if(created == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Contact information could not be added.")
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION, "/contact/" + created.getContact())
                .body(created);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<ContactInfo> getContactInfo(@PathVariable String contactId) {
        ContactInfo fetched = dispatcher.getContact(contactId);
        if(fetched == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Contact information could not be retrieved.")
                    .build();
        }
        return ResponseEntity.ok(fetched);
    }

    @GetMapping("/contact")
    public ResponseEntity<List<ContactInfo>> getAllContactInfo(@RequestParam(name = "addressId", required = false) Integer addressId) {
        return ResponseEntity.ok(dispatcher.getAllContacts(addressId));
    }

    @DeleteMapping("/address/{addressId}/contact/{contactId}")
    public ResponseEntity<ContactInfo> deleteContactInfo(@PathVariable int addressId, @PathVariable String contactId) {
        ContactInfo deleted = dispatcher.deleteContact(contactId, addressId);
        if(deleted == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Contact information could not be deleted.")
                    .build();
        }
        return ResponseEntity.ok(deleted);
    }
}
