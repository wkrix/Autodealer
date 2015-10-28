package hu.klayton.wade.spring.repository;

import hu.klayton.wade.spring.entity.Seller;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Walter Krix <walter.krix@inbuss.hu>
 */
public interface SellerRepository extends CrudRepository<Seller, Long> {

    Seller findByUsername(String username);
}
