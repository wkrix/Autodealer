package hu.klayton.wade.spring.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Entity
@Table(name = "Customer")
public class Customer implements Serializable {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String EMAIL_MESSAGE = "{email.message}";

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String name;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String country;

    @OneToMany(mappedBy = "customer")
    private List<Vehicle> vehicles = new ArrayList<>();

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Email(message = EMAIL_MESSAGE)
    private String email;

    public Customer() {
    }

    public Customer(String name, String country, String email) {
        this.name = name;
        this.country = country;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer other = (Customer) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return 7 * id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
