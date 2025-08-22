package application.main.controller;

import application.main.model.entity.Address;
import application.main.service.Dispatcher;
import application.main.service.Interfaces.IDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    private IDispatcher dispatcher = new Dispatcher();

    @PostMapping("/people/{personId}/addresses")
    public ResponseEntity<Address> addAddress(@PathVariable("personId") int personId, @RequestBody Address address) {
        Address created = dispatcher.createAddress(personId, address);
        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("addresses/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable("addressId") int addressId) {
        Address fetched = dispatcher.getAddress(addressId);
        if (fetched == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetched, HttpStatus.FOUND);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(@RequestParam(name = "personId", required = false) Integer personId) {
        return new ResponseEntity<>(dispatcher.getAllAddress(personId), HttpStatus.OK);
    }

    @PutMapping("/addresses")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        Address updated = dispatcher.updateAddress(address);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Address> deleteAddress(@PathVariable("addressId") int addressId) {
        Address deleted = dispatcher.deleteAddress(addressId);
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
