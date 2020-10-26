package com.example.UserMicroservice.Service;

import com.example.UserMicroservice.Entity.UserEntity;
import com.example.UserMicroservice.Repository.UserREpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
  @Autowired
    UserREpository userREpository;

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

}
