package application.main.Controller;

import application.main.Model.Entity.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @PostMapping("/people/{personId}/addresses")
    public ResponseEntity<Address> addAddress(@PathVariable("personId") int personId, @RequestBody Address address) {
        //TODO
        return null;
    }

    @GetMapping("/people/{personId}/addresses/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable("personId") int personId, @PathVariable("addressId") int addressId) {
        //TODO
        return null;
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(@RequestParam(name = "personId", required = false) Integer personId) {
        //TODO
        return null;
    }

    @PutMapping("/addresses")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        //TODO
        return null;
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Address> deleteAddress(@PathVariable("addressId") int addressId) {
        //TODO
        return null;
    }
}
