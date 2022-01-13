package com.example.services;

import com.example.entities.PlayList;
import com.example.entities.Track;
import com.example.entities.User;
import com.example.implementations.PlayListImpl;
import com.example.implementations.TrackImpl;
import com.example.implementations.UserImpl;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/music-system")
public class TrackResources {

    @GET
    @Path("{username}/{year}/myTracks")
    @Produces("application/json")
    public List<Track> displayTracks(@PathParam("username") String username,
                                     @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException, ParseException {
        List<Track> userTracks = new ArrayList<>();
        UserImpl repo1 = new UserImpl();
        if (repo1.login(username, year) == true) {
            Long id = repo1.getID(username, year).getId();
            User user = repo1.findById(id);
            userTracks = repo1.getUserTracks(user);
        }
        return userTracks;
    }

    @GET
    @Path("{username}/{year}/myTracks/{trackid}")
    @Produces("application/json")
    public Track specificTrack(@PathParam("trackid") Long trackid,
                               @PathParam("username") String username,
                               @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException, ParseException {
        List<Track> userTracks = new ArrayList<>();
        UserImpl repo1 = new UserImpl();
        Track track = null;
        if (repo1.login(username, year) == true) {
            Long id = repo1.getID(username, year).getId();
            User user = repo1.findById(id);
            userTracks = repo1.getUserTracks(user);
            for (Track x : userTracks) {
                if (x.getTrackId().compareTo(trackid) == 0) {
                    track = x;
                }
            }
        }
        return track;
    }




    //NEED TO EDIT PLAYLIST NAME, TRACK NAME

    //DELETE SPECIFIC TRACK
    @DELETE
    @Path("{username}/{year}/tracks/delete/{id}")
    @Produces("application/json")
    public String deleteTrack(@PathParam("id") Long id,
                              @PathParam("username") String username,
                              @PathParam("year") int year)
            throws ParserConfigurationException, IOException, SAXException {
        UserImpl repo1 = new UserImpl();
        TrackImpl repo = new TrackImpl();
        Track track = new Track();
        boolean verify = false;
        String delete = "unsuccessful";
        if (repo1.login(username, year) == true) {
            User user = repo1.findById(repo1.getID(username, year).getId());
            for (Track x : user.getTracks()) {
                if (x.getTrackId().compareTo(id) == 0) {
                    track = x;
                    verify = true;

                    //dao.meregeobjectuser                    user.getTracks().remove(x);
                    // user.
                }
            }
            if (verify) {
                //user.getTracks.remove(x);
                //update the user
                user.getTracks().remove(track);
                repo1.updateUser(user);
                delete = repo.delete(id);
                //merge user

            }
        }
        return delete;
    }

    //add new tracks and playlists??

    @POST
    @Path("{username}/{year}/tracks/create/")
    @Produces("application/json")
    public String createTrack(
            @PathParam("username") String username,
            @PathParam("year") int year, Track track) throws ParserConfigurationException, IOException, SAXException {
        TrackImpl repo = new TrackImpl();
        UserImpl repo1 = new UserImpl();
        String done = null;
        if (repo1.login(username, year) == true) {
            User user = repo1.findById(repo1.getID(username, year).getId());
            user.getTracks().add(track);
            done = repo.createTrack(track);
            //Have to manually create a user id though
            repo1.updateUser(user);
        }
        return done;
    }



    @PUT
    @Path("{username}/{year}/tracks/update/{id}")
    @Produces("application/json")
    public Track updateTrack(@PathParam("id") Long id, @PathParam("username") String username,
                             @PathParam("year") int year, Track track) throws ParserConfigurationException, IOException, SAXException {

        Track track1 = new Track();
        UserImpl repo1 = new UserImpl();
        TrackImpl repo = new TrackImpl();
        if (repo1.login(username, year) == true) {
            User user = repo1.findById(repo1.getID(username, year).getId());
            for (Track x : user.getTracks()) {
                if (x.getTrackId().compareTo(id) == 0) {
                    track1 = repo.updateTrackName(x, track);
                }
            }

        }

        return track1;
    }
}


//GENERIC
    //edit track name artist and genre
    //add track to playlist
    //do put method for
    //FIND ALL TRACKS METHOD
//    @GET
//    @Path("{username}/{year}/tracks")
//    @Produces("application/json")
//    public List<Track> getAll(@PathParam("username") String username,
//                              @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo = new UserImpl();
//        List <Track> tracks = new ArrayList<>();
//        if (repo.login(username, year) == true) {
//            // repo1 retrieve the users id and pass it into the desired method
//            TrackImpl repo1 = new TrackImpl();
//            //find all method should shoulod tracks with the logged in user id
//            tracks = repo1.findAll();
//        }
//        return tracks;
//    }

    // RETRIEVE SPECIFIC TRACK METHOD
//    @GET
//    @Path("{username}/{year}/tracks/{id}")
//    @Produces("application/json")
//    public Track getOne(@PathParam("id") Long id,
//                        @PathParam("username") String username,
//                        @PathParam("year") int year)
//            throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo = new UserImpl();
//        TrackImpl repo1 = new TrackImpl();
//        Track track = new Track();
//        if (repo.login(username, year) == true) {
//
//            User user = repo.findById(repo.getID(username, year).getId());
//            List userTracks = repo.getUserTracks(user);
//            track = repo1.findById(id);
//
//        }
//
//        return track;
//    }

    //DELETE SPECIFIC TRACK
//    @DELETE
//    @Path("{username}/{year}/tracks/delete/{id}")
//    @Produces("application/json")
//    public String delete(@PathParam("id") Long id,
//                         @PathParam("username") String username,
//                         @PathParam("year") int year)
//            throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo1 = new UserImpl();
//        String delete = "unsuccessful";
//        if (repo1.login(username, year) == true) {
//            TrackImpl repo = new TrackImpl();
//            delete = repo.delete(id);
//        }
//        return delete;
//    }
//}
