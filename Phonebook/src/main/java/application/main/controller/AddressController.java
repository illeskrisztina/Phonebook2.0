package application.main.controller;

import application.main.model.entity.Address;
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
public class AddressController {
    private final IDispatcher dispatcher;
    private static final String ERROR_HEADER = "Error";

    @PostMapping("/people/{personId}/addresses")
    public ResponseEntity<Address> addAddress(@PathVariable("personId") int personId, @RequestBody Address address) {
        Address created = dispatcher.createAddress(personId, address);
        if (created == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header(ERROR_HEADER, "Entity could not be created.")
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "addresses/" + created.getAddressId())
                .body(created);
    }

    @GetMapping("addresses/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable("addressId") int addressId) {
        Address fetched = dispatcher.getAddress(addressId);
        if (fetched == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER,"Address cannot be found.")
                    .build();
        }
        return ResponseEntity.ok(fetched);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(@RequestParam(name = "personId", required = false) Integer personId) {
        return ResponseEntity.ok(dispatcher.getAllAddress(personId));
    }

    @PutMapping("/addresses")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        Address updated = dispatcher.updateAddress(address);
        if (updated == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Address could not be updated.")
                    .build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Address> deleteAddress(@PathVariable("addressId") int addressId) {
        Address deleted = dispatcher.deleteAddress(addressId);
        if (deleted == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Address could not be deleted.")
                    .build();
        }
        return ResponseEntity.ok(deleted);
    }
}
