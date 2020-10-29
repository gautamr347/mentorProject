package com.example.UserMicroservice.Service;

import com.example.UserMicroservice.Entity.User;
import com.example.UserMicroservice.Entity.UserEntity;
import com.example.UserMicroservice.Repository.UserREpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service(value = "userService")
   public class UserServiceImp implements UserDetailsService, UserService {
  @Autowired
    UserREpository userREpository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

   public UserEntity findById(long id){
        return userREpository.findById(id).get();
    }
    public List<UserEntity> getallusers(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging= PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<UserEntity> pagedresult=userREpository.findAll(paging);
        if(pagedresult.hasContent()){
            return pagedresult.getContent();
        }
        else return new ArrayList<UserEntity>();

    }
   public void createUser(UserEntity user){
       userREpository.save(user);
    }
    public UserEntity findByUsernameignorecase(String username){
       return userREpository.findByUsernameIgnoreCase(username);
    }

   public UserEntity findByUsername(String username){
       return userREpository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity usr= userREpository.findByUsername(username);
       if(usr==null){throw new UsernameNotFoundException("Invalid username or password");}
       return new org.springframework.security.core.userdetails.User(usr.getUsername(), usr.getPassword(), getAuthority());
   }
    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    public void updatetoken(UserEntity user,String token) {
       UserEntity user1=findById(user.getId());
        if(user1 !=null){
            user1.setToken(token);
            userREpository.save(user1);
        }
    }
    public String save(User user){
       UserEntity newuser=new UserEntity();
       newuser.setTimestamp(user.getTimestamp());
       newuser.setFirstname(user.getFirstname());
       newuser.setLastname(user.getLastname());
       newuser.setContactnumber(user.getContactnumber());
       newuser.setPassword(bcryptEncoder.encode(user.getPassword()));
       newuser.setRole(user.getRole());
       newuser.setUsername(user.getUsername());
       UserEntity unt=findByUsernameignorecase(user.getUsername());
       if(unt!=null){return "username already exist";}
       else {userREpository.save(newuser);
       return "user created";}
    }
}
