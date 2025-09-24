package application.main.controller;

import application.main.model.entity.ContactInfo;
import application.main.model.entity.dto.ContactInfoDTO;
import application.main.service.interfaces.IDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ContactInfoController {
    private final IDispatcher dispatcher;
    private static final String ERROR_HEADER = "Error";

    @PostMapping("/addresses/{addressId}/contacts")
    public ResponseEntity<ContactInfoDTO> addContactInfo(@PathVariable(name = "addressId") int addressId, @RequestBody ContactInfo contactInfo) {
        ContactInfoDTO created = dispatcher.addContact(contactInfo, addressId);
        if(created == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Contact information could not be added.")
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/contact/" + created.getContact())
                .body(created);
    }

    @GetMapping("/contacts/{contactId}")
    public ResponseEntity<ContactInfoDTO> getContactInfo(@PathVariable(name = "contactId") String contactId) {
        try {
            ContactInfoDTO fetched = dispatcher.getContact(contactId);
            return ResponseEntity.ok(fetched);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Contact information could not be retrieved.")
                    .build();
        }
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactInfoDTO>> getAllContactInfo(@RequestParam(name = "addressId", required = false) Integer addressId) {
        return ResponseEntity.ok(dispatcher.getAllContacts(addressId));
    }

    @DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<Void> deleteContactInfo(@PathVariable(name = "contactId") String contactId) {
        dispatcher.deleteContact(contactId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
