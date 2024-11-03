
package org.example.projectbackend.User.Service;

import org.example.projectbackend.User.Entity.Address;
import org.example.projectbackend.User.Entity.User;
import org.example.projectbackend.User.Entity.AddressDTO;
import org.example.projectbackend.User.Entity.UserDTO;
import org.example.projectbackend.User.Repository.AddressRepository;
import org.example.projectbackend.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAddressService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO createUser(User user) {
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public AddressDTO addAddressToUser(Long userId, Address address) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            address.setUser(user);
            Address savedAddress = addressRepository.save(address);
            return convertToDTO(savedAddress);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public List<AddressDTO> getUserAddresses(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> user.getAddresses().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public UserDTO updateUser(Long userId, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            User savedUser = userRepository.save(user);
            return convertToDTO(savedUser);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public AddressDTO updateAddress(Long addressId, Address updatedAddress) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setStreet(updatedAddress.getStreet());
            address.setCity(updatedAddress.getCity());
            address.setCountry(updatedAddress.getCountry());
            Address savedAddress = addressRepository.save(address);
            return convertToDTO(savedAddress);
        } else {
            throw new RuntimeException("Address not found with ID: " + addressId);
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().name());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setAddresses(user.getAddresses().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return userDTO;
    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        return addressDTO;
    }
}
