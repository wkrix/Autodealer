package hu.klayton.wade.spring.dto;

import hu.klayton.wade.spring.entity.Seller;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class SellerDTO {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String username;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String password;


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

    public Seller createSeller() {
        return new Seller(getUsername(), getPassword(), "ROLE_USER");
    }

    @Override
    public String toString() {
        return "SellerDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
