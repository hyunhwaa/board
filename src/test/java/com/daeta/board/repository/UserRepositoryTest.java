//package com.daeta.board.repository;
//
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.UserRole;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void 저장된_사용자_찾기(){
//        //given
//        User user = User.builder()
//                .username("test")
//                .password("password")
//                .role(UserRole.USER)
//                .build();
//
//        userRepository.save(user);
//
//        //when
//        Optional<User> findUser = userRepository.findByUsername("test");
//
//        //then
//       assertEquals("test", findUser.get().getUsername());
//       assertEquals(UserRole.USER, findUser.get().getRole());
//    }
//
//    @Test
//    public void 중복_회원_찾기() throws Exception {
//           //given
//           User user1 = User.builder()
//                   .username("test")
//                   .password("password")
//                   .role(UserRole.USER)
//                   .build();
//
//            User user2 = User.builder()
//                    .username("test")
//                    .password("password")
//                    .role(UserRole.USER)
//                    .build();
//
//        //when
//        userRepository.save(user1);
//        try {
//            userRepository.save(user2);    //예외가 발생해야 한다.
//        }catch(IllegalArgumentException e){
//            return;
//        }
//        //then
//
//
//    }
//}