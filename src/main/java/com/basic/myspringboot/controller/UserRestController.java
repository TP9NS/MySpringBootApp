package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
//final 인 변수를 초기화하는 생성자를 자동으로 생성해주는 역할을 하는 롬복 어노테이션
@RequestMapping("/api/users")
@Profile("test")
public class UserRestController {
    private final UserRepository userRepository;

    //Constructor Injection
//    public UserRestController(UserRepository userRepository) {
//        System.out.println(">>> UserController " + userRepository.getClass().getName());
//        this.userRepository = userRepository;
//    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        //public <U> Optional<U> map(Function<? super T,? extends U> mapper)
        //Function 의 추상메서드 R apply(T t)
        ResponseEntity<User> responseEntity = optionalUser
                .map(user -> ResponseEntity.ok(user)) //User 가 있는 경우 200 status code
                //.orElse(ResponseEntity.notFound().build()); //User 가 없는 경우 404 status code
                .orElse(new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND));
        return responseEntity;

//        return optionalUser.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}/")  //http://localhost:8080/api/users/id/100
    public User getUserByEmail(@PathVariable String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User existUser = getExistUser(optionalUser);
        return existUser;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetail) {
        User existUser = getExistUser(userRepository.findById(id));
        //setter method 호출
        existUser.setName(userDetail.getName());
        User updatedUser = userRepository.save(existUser);
        return ResponseEntity.ok(updatedUser);
//        return ResponseEntity.ok(userRepository.save(existUser));
    }

    private User getExistUser(Optional<User> optionalUser) {
        User existUser = optionalUser
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        return existUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = getExistUser(userRepository.findById(id));
        userRepository.delete(user);
        return ResponseEntity.ok("User가 삭제 되었습니다!"); //status code 200
        //return ResponseEntity.noContent().build();  //status code 204
    }


}