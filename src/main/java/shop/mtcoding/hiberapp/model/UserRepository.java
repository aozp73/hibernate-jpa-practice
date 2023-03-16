package shop.mtcoding.hiberapp.model;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public User save(User user) {
        // DB에 영구히 기록하다
        em.persist(user);
        return user;
    }

    @Transactional
    public User update(User user) {
        return em.merge(user);
    }

    @Transactional
    public void delete(User user) {
        em.remove(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll(int page) {
        // findAll은 제공해주지 않음. 따라서 Query를 짜야 함
        // em.createQuery("select u from User u where u.id = 1");
        return em.createQuery("select u from User u", User.class)
                .setFirstResult(page)
                .setMaxResults(2)
                .getResultList();
    }

    public List<User> findAll(int page, int row) {
        // findAll은 제공해주지 않음. 따라서 Query를 짜야 함
        // em.createQuery("select u from User u where u.id = 1");
        return em.createQuery("select u from User u", User.class)
                .setFirstResult(page)
                .setMaxResults(row)
                .getResultList();
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
