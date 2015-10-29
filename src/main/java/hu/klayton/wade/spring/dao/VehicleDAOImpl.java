package hu.klayton.wade.spring.dao;

import hu.klayton.wade.spring.entity.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 *         Ez az osztaly nem hasznalt, miota helyettesittem Spring Data-val, de referencianak fentmarad.
 */
@Repository
@Transactional
public class VehicleDAOImpl implements VehicleDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void addVehicle(Vehicle vehicle) {
        em.persist(vehicle);
    }

    @Override
    public void deleteVehicle(int id) {
        final Query q = em.createQuery("DELETE FROM Vehicle WHERE id=" + id);
        q.executeUpdate();

    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        em.merge(vehicle);
    }

    @Override
    public Vehicle getVehicle(int id) {
        return em.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        Query query =
                em.createQuery("FROM Vehicle");

        return (List<Vehicle>) query.getResultList();
    }
}
