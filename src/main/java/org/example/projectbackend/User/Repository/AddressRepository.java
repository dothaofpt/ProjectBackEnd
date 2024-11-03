
package org.example.projectbackend.User.Repository;

import org.example.projectbackend.User.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
