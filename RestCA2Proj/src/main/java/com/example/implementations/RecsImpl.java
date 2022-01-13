package com.example.implementations;

import com.example.dao.SystemDao;
import com.example.entities.Reccomendations;
import com.example.repositories.ReccomendationRepo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class RecsImpl implements ReccomendationRepo {
    SystemDao dao = new SystemDao();
    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("DianasPU");//


    @Override
    public List<Reccomendations> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Reccomendations.findAll", Reccomendations.class).getResultList();
    }
    @Override
    public Reccomendations findById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em.find(Reccomendations.class, id);
    }

    @Override
    public String delete(Long id) {
        EntityManager em = emf.createEntityManager();
        Reccomendations rec = em.find(Reccomendations.class, id);
        dao.remove(rec);
        return "Delete complete";
    }

    public Reccomendations createTecSong(Reccomendations song) {
        String status = "No success";
        if(song.getArtist()!=null & song.getImg_url()!=null&song.getTitle()!=null&song.getYear()!=null) {
            dao.persistObject(song);
            status = "success";
        }
        return song;
    }

    public String updateSong(Reccomendations r, Reccomendations song) {
        String status = "No success";
        if(song.getArtist()!=null) {
            r.setArtist(song.getArtist());
            dao.mergeObject(song);
            status = "success";
        }
        return status;

    }
}
