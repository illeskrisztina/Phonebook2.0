package application.main.controller;

import application.main.model.entity.Address;
import application.main.model.entity.dto.AddressDTO;
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
public class AddressController {
    private final IDispatcher dispatcher;
    private static final String ERROR_HEADER = "Error";

    @PostMapping("/people/{personId}/addresses")
    public ResponseEntity<AddressDTO> addAddress(@PathVariable("personId") int personId, @RequestBody Address address) {
        AddressDTO created = dispatcher.createAddress(personId, address);
        if (created == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header(ERROR_HEADER, "Entity could not be created.")
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "addresses/" + created.getId())
                .body(created);
    }

    @GetMapping("addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable("addressId") int addressId) {
        try {
            AddressDTO fetched = dispatcher.getAddress(addressId);
            return ResponseEntity.ok(fetched);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER,"Address cannot be found.")
                    .build();
        }
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(@RequestParam(name = "personId", required = false) Integer personId) {
        return ResponseEntity.ok(dispatcher.getAllAddress(personId));
    }

    @PutMapping("/addresses")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody Address address) {
        AddressDTO updated = dispatcher.updateAddress(address);
        if (updated == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Address could not be updated.")
                    .build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("addressId") int addressId) {
        dispatcher.deleteAddress(addressId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
