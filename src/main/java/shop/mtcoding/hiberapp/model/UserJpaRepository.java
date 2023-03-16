package shop.mtcoding.hiberapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    // entity의 타입, entity의 primary 넣어 주면 됨

}
