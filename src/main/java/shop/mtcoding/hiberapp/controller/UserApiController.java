package shop.mtcoding.hiberapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.hiberapp.model.User;
import shop.mtcoding.hiberapp.model.UserRepository;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserRepository userRepository;

    @PostMapping("/users") // users라는 컬렉션에 insert 할 게 이런 의미
    public ResponseEntity<?> addUser(User user) {
        User userPS = userRepository.save(user);
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    // 주소로 받은 id는 절대 신뢰하면 안됨
    // 따라서, 검증 해야 함
    @PutMapping("/users/{id}") // users라는 컬렉션에 insert 할 게 이런 의미
    public ResponseEntity<?> updateUser(@PathVariable Long id, User user) {
        // 검증부터 해야 update에서 트랜잭션 구간 낭비가 없음
        User userPS = userRepository.findById(id);
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }

        userPS.update(user.getPassword(), user.getEmail());
        User updateUserPS = userRepository.update(userPS);

        return new ResponseEntity<>(updateUserPS, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, User user) {
        User userPS = userRepository.findById(id);
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(userPS);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findUsers(@RequestParam(defaultValue = "0") int page) {
        List<User> userListPS = userRepository.findAll(page, 2);
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserOne(@PathVariable Long id) {
        User userPS = userRepository.findById(id);
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }
}
