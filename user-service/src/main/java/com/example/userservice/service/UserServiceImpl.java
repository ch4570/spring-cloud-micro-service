package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
    private final Environment env;


    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);
        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDto userDto = mapper.map(userEntity, UserDto.class);

//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
        /* Using as RestTemplate */
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET,
//                null, new ParameterizedTypeReference<>() {});
//
//        List<ResponseOrder> orders = orderListResponse.getBody();

        /* Using as FeignClients */
        /* Feign Exception Handling */
        List<ResponseOrder> orders = null;

        try {
            orders = orderServiceClient.getOrder(userId);
        } catch (FeignException ex) {
            log.error(ex.getMessage());
        }

        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public List<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
