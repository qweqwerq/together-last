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
        User originalUserInfo = em.find(User.class, user.getUserIdx());
        originalUserInfo.setUserPw(user.getUserPw());
        originalUserInfo.setUserPhone(user.getUserPhone());
        originalUserInfo.setUserName(user.getUserName());
        originalUserInfo.setAddress(user.getAddress());
        originalUserInfo.setRole(user.getRole());

        em.merge(originalUserInfo);
    }

    public List<User> findById(String userId) {
        return em.createQuery("select m from User m where m.userId =:userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<User> findByPhone(String phone) {
        return em.createQuery("select m from User m where m.userPhone =:phone", User.class)
                .setParameter("phone", phone)
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

    public List<User> findByReport() {
        return em.createQuery("select m from User m where m.report =: true")
                .setParameter("true", true)
                .getResultList();

    }

    public List<User> findByRoomId(Long userIdx) {
        return em.createQuery("select m from User m where m.userIdx =:userIdx", User.class)
                .setParameter("userIdx", userIdx)
                .getResultList();
    }
}
