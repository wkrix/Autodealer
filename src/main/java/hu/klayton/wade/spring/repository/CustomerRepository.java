package hu.klayton.wade.spring.repository;

import hu.klayton.wade.spring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    Customer findByName(String name);

    List<Customer> findByEmail(String email);

    List<Customer> findByCountry(String country);


}
