package com.gateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService{
   //@Autowired
   //private UserRepository userDao;   
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;      
   @Override
   /*public User save(LoginUser user) {
         User newUser = new User();
         newUser.setUsername(user.getUsername());
         newUser.setPassword(passwordEncoder.encode(user.getPassword()));
         return userDao.save(newUser);
    }*/
   
   public UserDetails loadUserByUsername(String userId) throws
               UsernameNotFoundException {
         //User user = userDao.findByUsername(userId);
         /*if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
         }*/
         //return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
     return new org.springframework.security.core.userdetails.User("admin", passwordEncoder.encode("123456"), getAuthority());
 
   }
   
   public Collection<? extends GrantedAuthority> getAuthority() {
	   
	   List<String> privileges = new ArrayList<>();
	   
	   privileges.add("ADMIN");
	   //privileges.add("USER");
	   
       return getGrantedAuthorities(privileges);
   }
   
   private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
       List<GrantedAuthority> authorities = new ArrayList<>();
       for (String privilege : privileges) {
           authorities.add(new SimpleGrantedAuthority(privilege));
       }
       return authorities;
   }
  // Other service methods

}