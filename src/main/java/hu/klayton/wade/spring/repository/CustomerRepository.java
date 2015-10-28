package hu.klayton.wade.spring.repository;

import hu.klayton.wade.spring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Walter Krix <walter.krix@inbuss.hu>
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    Customer findByName(String name);

    List<Customer> findByEmail(String email);

    List<Customer> findByCountry(String country);


}
