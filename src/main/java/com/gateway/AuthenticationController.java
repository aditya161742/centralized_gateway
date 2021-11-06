package com.gateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
   // @Autowired
    //private UserService userService;
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    //public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser)  throws AuthenticationException {
    public String generateToken() throws AuthenticationException {
    		
    	
    	final Authentication authentication = authenticationManager.authenticate(
              /*  new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )*/
    	        new UsernamePasswordAuthenticationToken(
                        "admin",
                        "123456"
                )
        
        );
    	System.out.println("is credentials authenticated ?? == "+authentication.isAuthenticated());
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //final User user = userService.findOne(loginUser.getUsername());
        
        UserDetail userDetails = new UserDetail("admin", "123456",true, true,false, true,getAuthority());
        
        final User user = userDetails;
        final String token = jwtTokenUtil.generateToken(user);
        return token;
        //return ResponseEntity.ok(new AuthToken(token));
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
    
}
