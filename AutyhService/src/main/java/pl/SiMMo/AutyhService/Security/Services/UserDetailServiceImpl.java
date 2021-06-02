package pl.SiMMo.AutyhService.Security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.SiMMo.AutyhService.Models.User;
import pl.SiMMo.AutyhService.Models.UserRepo;


import javax.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username : " + username)
                );

        return UserPrinciple.build(user);
    }
}
