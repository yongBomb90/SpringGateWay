package com.example.microservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;
    static {
        users.add(new User(1,"first", new Date(), "qwer1","900818-1111111"));
        users.add(new User(2,"second", new Date(), "qwer1","900818-1111111"));
        users.add(new User(3,"third", new Date(), "qwer1","900818-1111111"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if ( user.getId() == null ) {
            user.setId( ++userCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for ( User user : users) {
            if ( user.getId() == id ) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id) {
       Iterator<User> iterable =  users.iterator();

       while (iterable.hasNext()) {
           User user = iterable.next();

           if( user.getId() == id) {
               iterable.remove();
               return user;
           }
       }

       return null;
    }


}
