package hu.klayton.wade.spring.repository;

import hu.klayton.wade.spring.entity.Seller;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public interface SellerRepository extends CrudRepository<Seller, Long> {

    Seller findByUsername(String username);
}
