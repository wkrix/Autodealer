package hu.klayton.wade.spring.service;

import hu.klayton.wade.spring.entity.Seller;
import hu.klayton.wade.spring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Service
public class UserService implements UserDetailsService {


    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    protected void initialize() {
        save(new Seller("dummySeller", "password", "ROLE_USER"));
        save(new Seller("walter", "jelszo", "ROLE_USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByUsername(username);
        if (seller == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(seller);
    }

    private UserDetails createUser(Seller seller) {
        return new User(seller.getUsername(), seller.getPassword(), Collections.singleton(new SimpleGrantedAuthority(seller.getRole())));
    }


    public Seller save(Seller seller) {
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        return sellerRepository.save(seller);
    }

    public void signin(Seller seller) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(seller));
    }

    private Authentication authenticate(Seller seller) {
        return new UsernamePasswordAuthenticationToken(createUser(seller), null, Collections.singleton(new SimpleGrantedAuthority(seller.getRole())));
    }

    public void remove(Seller seller) {
        sellerRepository.delete(seller);
    }

}
