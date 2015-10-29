package hu.klayton.wade.spring.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Entity
@Table(name = "Seller")
public class Seller implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role = "ROLE_USER";

    public Seller(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Seller() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
