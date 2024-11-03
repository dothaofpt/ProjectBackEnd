package org.example.projectbackend.User.Controller;

import org.example.projectbackend.User.Entity.Address;
import org.example.projectbackend.User.Entity.AddressDTO;
import org.example.projectbackend.User.Entity.User;
import org.example.projectbackend.User.Entity.UserDTO;
import org.example.projectbackend.User.Service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userAddressService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody User user) {
        return userAddressService.createUser(user);
    }

    @PostMapping("/{userId}/addresses")
    public AddressDTO addAddressToUser(@PathVariable Long userId, @RequestBody AddressDTO addressDTO) {
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        return userAddressService.addAddressToUser(userId, address);
    }

    @GetMapping("/{userId}/addresses")
    public List<AddressDTO> getUserAddresses(@PathVariable Long userId) {
        return userAddressService.getUserAddresses(userId);
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        return userAddressService.updateUser(userId, updatedUser);
    }

    @PutMapping("/addresses/{addressId}")
    public AddressDTO updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO updatedAddressDTO) {
        Address updatedAddress = new Address();
        updatedAddress.setStreet(updatedAddressDTO.getStreet());
        updatedAddress.setCity(updatedAddressDTO.getCity());
        updatedAddress.setCountry(updatedAddressDTO.getCountry());
        return userAddressService.updateAddress(addressId, updatedAddress);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userAddressService.deleteUser(userId);
    }

    @DeleteMapping("/addresses/{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        userAddressService.deleteAddress(addressId);
    }
}
