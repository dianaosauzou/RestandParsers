package com.example.implementations;

import com.example.dao.SystemDao;
import com.example.entities.PlayList;
import com.example.entities.Reccomendations;
import com.example.entities.Track;
import com.example.entities.User;
import com.example.parsers.ParseJSON;
import com.example.parsers.ParseXML;
import com.example.repositories.UserRepo;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class UserImpl implements UserRepo {
    SystemDao dao = new SystemDao();
    ParseXML xmlParser = new ParseXML();
    ParseJSON jsonParser = new ParseJSON();
    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("DianasPU");//

    public UserImpl() throws ParserConfigurationException, IOException, SAXException {
        //if i were to have a many to many method that allowed for duplicate key entrys, meaning the tracks in the playlist would merge with the tracks in the
        //tracks playlist, that would me okay
    }

    //get user tracks

    @Override
    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em.find(User.class, id);
    }
    //I can modify the id by having it join with the userid?

    @Override
    public Boolean login(String user, int joinYear) {
        List<User> list = findAll();
        Boolean b = false;
        for (User u: list){
            if(user.equalsIgnoreCase(u.getUsername())&& joinYear == u.getJoinDate())
                b = true;
        }
        return b;
    }

    public List<Track> getUserTracks(User user){
        return user.getTracks();
    }

    public User getID(String user, int joinYear) {
        List<User> list = findAll();
        User o = new User();
        for (User u: list){
            if(user.equalsIgnoreCase(u.getUsername())&& joinYear == u.getJoinDate()) {
                o = u;
            }
        }
        return o;
    }

    public String loadSystem(Long id, User user) throws ParserConfigurationException, IOException, SAXException, ParseException {
        xmlParser.setFile(id);
        List<Track> track = xmlParser.parseTracks();
        for(Track x: track){
            x.setTrackId((long) Integer.parseInt(user.getId()+""+x.getTrackId()));
            dao.persistObject(x);
        }
        user.setTracks(track);
        // i need to find where
        List<PlayList> playList = xmlParser.parsePlaylists();

//        //findAll where user = whatever user is logged in
        for(PlayList x: playList) {
            for(Track a: x.getTracks()){
                a.setTrackId((long) Integer.parseInt(user.getId()+""+a.getTrackId()));
            }
            dao.persistObject(x);
        }
        user.setPlaylist(playList);

        //we'll do an update or merge

        List<Reccomendations> rex = jsonParser.ParseJSON();
        for(Reccomendations x: rex){
            dao.persistObject(x);
        }
        user.setRecSongs(rex);
        dao.mergeObject(user);
        return "Upload complete";
    }

    public void updateUser(User user) {
        dao.mergeObject(user);
    }

    //I have to do a dao persist and then a merge fine, but then how do i
}
