package com.example.implementations;

import com.example.dao.SystemDao;
import com.example.entities.Track;
import com.example.parsers.ParseXML;
import com.example.repositories.TrackRepo;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class TrackImpl implements TrackRepo {
    ParseXML xmlParser = new ParseXML();
    SystemDao dao = new SystemDao();
    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("DianasPU");//


    public TrackImpl() throws ParserConfigurationException, IOException, SAXException {
    }

    @Override
    public List<Track> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Track.findAll", Track.class).getResultList();
        //when i do this find all method i need to return tracks belonging to logged in user
    }

    @Override
    public Track findById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em.find(Track.class, id);
    }

    @Override
    public String delete(Long id) {
        EntityManager em = emf.createEntityManager();
        Track track = em.find(Track.class, id);
        dao.remove(track);
        return "Delete complete";
    }

    @Override
    public Track findByTrackId(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Track.findByTrackID", Track.class).getSingleResult();
    }

    public void createTrack() {
        List<Track> tracks = xmlParser.parseTracks();
        for (Track x : tracks) {
            dao.persistObject(x);
        }

    }

    public String createTrack(Track track) {
        dao.persistObject(track);
        return "Create is successful";

    }

    public Track updateTrackName(Track x, Track track) {
        if(track.getName()!=null){
            x.setName(track.getName());
        }
        return x;
    }

//    public Track returnUserTrack(User user, Long id) {
//        Track track = new Track();
//        for(Track x: user.getTracks()){
//                System.out.println(x.getTrackId());
//                if(x.getTrackId()==id)
//                    track = findById(id);
//        }
//        return track;
//    }

}
