package com.example.implementations;

import com.example.dao.SystemDao;
import com.example.entities.PlayList;
import com.example.repositories.PlayListRepo;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;


public class PlayListImpl implements PlayListRepo {
    @Resource
    UserImpl userResource = new UserImpl();


    SystemDao dao = new SystemDao();
    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("DianasPU");//


    public PlayListImpl() throws ParserConfigurationException, IOException, SAXException {
    }

    //Returns all the playlists in the database
    @Override
    //return playsliost from user
    public List<PlayList> findAll() {
        //findall method should return tracks, of the users id
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("PlayList.findAll", PlayList.class).getResultList();
    }

    //Returns specific playlist in the database
    @Override
    public PlayList findById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em.find(PlayList.class, id);
    }

    @Override
    public String delete(Long id) {
        EntityManager em = emf.createEntityManager();
        PlayList playList = em.find(PlayList.class, id);
        dao.remove(playList);
        return "Delete complete";
    }

    public String createPlayList(PlayList playList) {
        dao.persistObject(playList);
        return "Create complete";
    }

    public PlayList updatePlayListName(PlayList x, PlayList playList) {
        if(playList.getName()!=null) {
            x.setName(playList.getName());
        }
        dao.mergeObject(x);
        return x;
    }

    //parses the playlists to the database





}
