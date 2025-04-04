package com.menglang.bong_rumluos.Bong_rumluos.security.userDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.User;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.UserRepository;
import com.menglang.bong_rumluos.Bong_rumluos.security.userPrincipal.AuthoritiesExtraction;
import com.menglang.bong_rumluos.Bong_rumluos.security.userPrincipal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthoritiesExtraction authoritiesExtraction;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            return customUserDetail(username);
        }catch (UsernameNotFoundException userEx){
            throw new UsernameNotFoundException(username+" Not Found");
        }
    }

    private UserPrincipal customUserDetail(String username){
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        return  new UserPrincipal(user.getUsername(),user.getPassword(),authoritiesExtraction.extractRoleGrantAuthorities(user.getRoles()));
    }


}
