package ait.cohort63.shop_63.security;

import ait.cohort63.shop_63.repository.interfaces.UserRepositoty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepositoty userRepositoty;

    public UserDetailServiceImpl(UserRepositoty userRepositoty) {
        this.userRepositoty = userRepositoty;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoty.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
