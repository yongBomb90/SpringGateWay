package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{



    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder;

    RestTemplate restTemplate;

    Environment environment;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RestTemplate restTemplate, Environment environment) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null ) {
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getEmail(),userEntity.getEncryptedPwd(),true,true,true,true,new ArrayList<>());
    }
    @Override
    public UserDTO createUser(UserDTO param) {
        param.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity user = modelMapper.map(param, UserEntity.class);
        user.setEncryptedPwd(passwordEncoder.encode(param.getPwd()));

        userRepository.save(user);

        return  modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw  new UsernameNotFoundException("User Not found");

        UserDTO userDTO = new ModelMapper().map(userEntity,UserDTO.class);

//        List<ResponseOrder> orders = new ArrayList<>();
        String orderUrl = String.format(environment.getProperty("order_service.url"),userId) ;
        //렛스트템플릿사용
        ResponseEntity<List<ResponseOrder>> orderListResponse =
        restTemplate.exchange(
                orderUrl
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<List<ResponseOrder>>() {
                }
        );
        List<ResponseOrder> orders  = orderListResponse.getBody();
        userDTO.setOrders(orders);
        return userDTO;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserDetailsByEmail(String userName) {
        UserEntity userEntity = userRepository.findByEmail(userName);
        UserDTO userDTO = new ModelMapper().map(userEntity,UserDTO.class);
        if ( userEntity == null ) {
            throw new UsernameNotFoundException(userName);
        }
        return userDTO;
    }


}
