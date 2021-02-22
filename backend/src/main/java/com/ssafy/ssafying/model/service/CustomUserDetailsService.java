package com.ssafy.ssafying.model.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ssafy.ssafying.model.dto.UserDto;
// import com.ssafy.ssafying.model.dto.UserPrincipalDto;
import com.ssafy.ssafying.model.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("key", "email");
            map.put("word", email);
            UserDto userAuth = userMapper.userSelectByKeyWord(map);
            // UserPrincipalDto userPrincipalDto = new UserPrincipalDto(userAuth);

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(userAuth.getRole()));
            if(email != null) return new User(userAuth.getEmail(), userAuth.getPassword(), grantedAuthorities);
            
            throw new Exception();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Email " + email + " Not Found!");
        }
    }

    public UserDto authenticateByEmailAndPassword(String email, String password) {
        
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            return userMapper.userLogin(map);

        } catch (Exception e) {
            return null;
        }
    }
}