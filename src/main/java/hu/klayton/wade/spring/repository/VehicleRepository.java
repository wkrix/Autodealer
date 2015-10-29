package hu.klayton.wade.spring.repository;

import hu.klayton.wade.spring.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByStatus(Vehicle.Status status);

    List<Vehicle> findByCustomerId(int id);

}
