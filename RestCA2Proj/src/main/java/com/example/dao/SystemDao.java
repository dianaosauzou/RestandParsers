package com.example.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SystemDao {
    private EntityManagerFactory emf =
            Persistence. createEntityManagerFactory("DianasPU");

    public SystemDao() {
    }

    public void persistObject(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        em.close();
    }


    public Object mergeObject(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Object o = em.merge(object);
        em.getTransaction().commit();
        em.close();
        return o;
    }

    public void remove(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(object));
        em.getTransaction().commit();
        em.close();
    }


}
