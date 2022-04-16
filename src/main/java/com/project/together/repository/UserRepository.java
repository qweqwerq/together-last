package com.project.together.repository;

import com.project.together.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserRepository {


    private final EntityManager em;

    public void save (User user) {
        em.persist(user);
    }

    public void merge(User user){
        if(user.getUserId() != null){
            em.merge(user);
        }
    }

    public List<User> findById(String userId) {
        return em.createQuery("select m from User m where m.userId =:userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public User findByIdx(Long userIdx) {
        return em.find(User.class, userIdx);
    }

    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }
    public Optional<User> findByLoginId(String userId) {
        return this.findAll().stream()
                .filter(m -> m.getUserId().equals(userId))
                .findFirst();
    }

}
