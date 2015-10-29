package hu.klayton.wade.spring.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Entity
@Table(name = "Vehicle")
public class Vehicle implements Serializable {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String NOT_NULL_MESSAGE = "{notNull.message}";
    private static final String MIN_MESSAGE = "{min.message}";

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String vehicleName;

    @Min(value = 2, message = MIN_MESSAGE)
    private int numberOfWheels;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String color;

    @NotNull(message = NOT_NULL_MESSAGE)
    private Type type;

    @ManyToOne
    private Customer customer;

    @NotNull(message = NOT_NULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    public Status status;

    public Vehicle() {
        this.status = Status.FOR_SALE;
    }

    public Vehicle(String vehicleName, int numberOfWheels, String color, Type type) {
        this.vehicleName = vehicleName;
        this.numberOfWheels = numberOfWheels;
        this.color = color;
        this.type = type;
        this.status = Status.FOR_SALE;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleName='" + vehicleName + '\'' +
                ", numberOfWheels=" + numberOfWheels +
                ", color='" + color + '\'' +
                ", type=" + type +
                ", customer=" + customer +
                ", status=" + status +
                '}';
    }

    public enum Type {

        BIKE,
        AUTO

    }

    public enum Status {

        SOLD,
        FOR_SALE

    }

}
