package hu.klayton.wade.spring.dao;

import hu.klayton.wade.spring.entity.Vehicle;

import java.util.List;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 *         Ez az osztaly nem hasznalt, miota helyettesittem Spring Data-val, de referencianak fentmarad.
 */
public interface VehicleDAO {

    void addVehicle(Vehicle vehicle);

    void deleteVehicle(int id);

    void updateVehicle(Vehicle vehicle);

    Vehicle getVehicle(int id);

    List<Vehicle> getAllVehicle();
}
