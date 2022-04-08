package com.project.together.repository;

import com.project.together.entity.Category;
import com.project.together.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findByName(String name) {
        return em.createQuery("select i from Item i where i.name like :name")
                .getResultList();
    }

    /*public List<Item> findByCategory(Category category) {
        return em.createQuery("select i from Item i where i.categories")
    }*/
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
