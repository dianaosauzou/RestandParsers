package com.example.services;

import com.example.entities.PlayList;
import com.example.entities.Reccomendations;
import com.example.entities.Track;
import com.example.entities.User;
import com.example.implementations.PlayListImpl;
import com.example.implementations.RecsImpl;
import com.example.implementations.TrackImpl;
import com.example.implementations.UserImpl;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/music-system")
public class RecResources {
    @PUT
    @Path("{username}/{year}/recs/update/{id}")
    @Produces("application/json")
    public String updateRecSong(@PathParam("id")Long id, @PathParam("username") String username,
                                @PathParam("year") int year, Reccomendations rec) throws ParserConfigurationException, IOException, SAXException {
        String status = "No success";
        RecsImpl recs = new RecsImpl();
        UserImpl repo1 = new UserImpl();
        if (repo1.login(username, year) == true) {
            User user = repo1.findById(repo1.getID(username, year).getId());
            for (Reccomendations r : user.getRecSongs()) {
                if (r.getId().compareTo(id) == 0) {
                    status = recs.updateSong(r, rec);

                }
            }
        }
        return status;
    }

    @POST
    @Path("{username}/{year}/recs/create")
    @Produces("application/json")
    public Reccomendations createRecSong(Reccomendations song){
        RecsImpl recs = new RecsImpl();
        return recs.createTecSong(song);
    }

    @GET
    @Path("{username}/{year}/recs/")
    @Produces("application/xml")
    public List<Reccomendations> getRecSong(@PathParam("username") String username,
                                            @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
        List<Reccomendations> recs = new ArrayList<>();
        UserImpl repo1 = new UserImpl();
        if (repo1.login(username, year) == true) {
            Long id = repo1.getID(username, year).getId();
            User user = repo1.findById(id);
            recs = user.getRecSongs();
        }
        return recs;

    }
    @DELETE
    @Path("{username}/{year}/recs/delete/{id}")
    @Produces("application/json")
    public void deleteRecSong(@PathParam("id") Long id, @PathParam("username") String username,
                              @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
        List<Reccomendations> recs = new ArrayList<>();
        RecsImpl recsrepo = new RecsImpl();
        UserImpl repo1 = new UserImpl();
        if (repo1.login(username, year) == true) {
            Long ids = repo1.getID(username, year).getId();
            User user = repo1.findById(id);
            Reccomendations b = new Reccomendations();
            boolean verify = false;
            for (Reccomendations x : user.getRecSongs()) {
                if (x.getId().compareTo(id) == 0) {
                    verify = true;
                    b = x;

                }
            }
            if (verify) {
                recsrepo.delete(id);
                user.getRecSongs().remove(b);
                repo1.updateUser(user);
            }
        }



    }
}

///GENERIC
//    //edit track name artist and genre
//    //add track to playlist
//    //Print all recommendations
//    @GET
//    @Path("{username}/{year}/recommendations")
//    @Produces("application/json")
//    public List<Reccomendations> getAll(@PathParam("username") String username,
//                                        @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo1 = new UserImpl();
//        List <Reccomendations> recs = new ArrayList<>();
//        if (repo1.login(username, year) == true) {
//            RecsImpl repo = new RecsImpl();
//            recs= repo.findAll();
//        }
//        return recs;
//    }
//
//    //Print one recommendation
//    @GET
//    @Path("{username}/{year}/recommendations/{id}")
//    @Produces("application/json")
//    public Reccomendations getOne(@PathParam("id") Long id,
//                                  @PathParam("username") String username,
//                                  @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo1 = new UserImpl();
//        Reccomendations rec = new Reccomendations();
//        if (repo1.login(username, year) == true) {
//            RecsImpl repo = new RecsImpl();
//            rec = repo.findById(id);
//        }
//        return rec;
//    }
//
//    //Delete specific recommendation
//    @DELETE
//    @Path("{username}/{year}/recommendations/delete/{id}")
//    @Produces("application/json")
//    public String delete(@PathParam("id") Long id,
//                         @PathParam("username") String username,
//                         @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo1 = new UserImpl();
//        String delete = "unsuccessful";
//        if (repo1.login(username, year) == true) {
//           // repo1 retrieve the users id and pass it into the desired method
//            RecsImpl repo = new RecsImpl();
//            //Pass in the user id
//            return repo.delete(id);
//        }
//        return delete;
//    }
//}
