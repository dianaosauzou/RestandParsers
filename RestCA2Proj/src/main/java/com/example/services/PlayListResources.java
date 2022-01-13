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
import java.util.Objects;

//Musicsystem forward slash user id? + password
@Path("/music-system/")
public class PlayListResources{
    @POST
    @Path("{username}/{year}/myPlaylist/create/")
    @Produces("application/json")
    public String createPlaylist(
            @PathParam("username") String username,
            @PathParam("year") int year, PlayList playList) throws ParserConfigurationException, IOException, SAXException {
        UserImpl repo1 = new UserImpl();
        PlayListImpl repo = new PlayListImpl();

        String doCreate = "null";
        if (repo1.login(username, year) == true) {
            User user = repo1.findById(repo1.getID(username, year).getId());
            user.getPlaylist().add(playList);
            doCreate = repo.createPlayList(playList);
            repo1.updateUser(user);
        }
        return doCreate;
    }

    @PUT
    @Path("{username}/{year}/myPlaylist/update/{id}")
    @Produces("application/json")
    public PlayList updatePlayList(@PathParam("id") Long id, @PathParam("username") String username,
                                   @PathParam("year") int year, PlayList playList) throws ParserConfigurationException, IOException, SAXException {
        PlayList p1 = new PlayList();
        UserImpl repo1 = new UserImpl();
        PlayListImpl repo = new PlayListImpl();
        if (repo1.login(username, year) == true) {
            User user = repo1.findById(repo1.getID(username, year).getId());
            for (PlayList x : user.getPlaylist()) {
                if (x.getId().compareTo(id) == 0) {
                    p1 = repo.updatePlayListName(x, playList);
                }
            }

        }

        return p1;
    }
    @DELETE
    @Path("{username}/{year}/myPlaylists/delete/{id}")
    @Produces("application/json")
    public String deletePlayList(@PathParam("id") Long id,
                                 @PathParam("username") String username,
                                 @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
        UserImpl repo1 = new UserImpl();
        PlayListImpl repo = new PlayListImpl();

        boolean verify = false;
        String delete = "unsuccessful";
        if (repo1.login(username, year)) {
            User user = repo1.findById(repo1.getID(username, year).getId());
            for (PlayList x : user.getPlaylist()) {
                if (x.getId().compareTo(id) == 0) {
                    verify = true;
                    user.getPlaylist().remove(x);

                }
            }

            if (verify) {
                repo1.updateUser(user);
                delete = repo.delete(id);
                //merge user
            }
        }
        return delete;
    }

    @GET
    @Path("{username}/{year}/myPlaylists")
    @Produces("application/json")
    public List<PlayList> displayPlaylists(@PathParam("username") String username,
                                           @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException, ParseException {
        List<PlayList> userPlaylist = new ArrayList<>();
        UserImpl repo1 = new UserImpl();
        if (repo1.login(username, year) == true) {
            Long id = repo1.getID(username, year).getId();
            User user = repo1.findById(id);
            userPlaylist = user.getPlaylist();
        }
        return userPlaylist;
    }

    @GET
    @Path("{username}/{year}/myPlaylists/{playid}")
    @Produces("application/json")
    public PlayList specificPlaylists(@PathParam("playid") Long playid,
                                      @PathParam("username") String username,
                                      @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException, ParseException {
        List<PlayList> userPlaylist = new ArrayList<>();
        PlayList playList = new PlayList();

        UserImpl repo1 = new UserImpl();
        if (repo1.login(username, year) == true) {
            Long id = repo1.getID(username, year).getId();
            User user = repo1.findById(id);
            userPlaylist = user.getPlaylist();
            for (PlayList x : userPlaylist) {
                if (x.getId().compareTo(playid) == 0) {
                    playList = x;
                }
            }
        }
        return playList;
    }
}
//
////Move track from one playlist to another
//    //edit playlist name
//    //delete song from playlist
//
//    //Prints all playlists
//    @GET
//    @Path("{username}/{year}/playlists")
//    @Produces("application/json")
//    public List<PlayList> getAll(@PathParam("username") String username,
//                                 @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo1 = new UserImpl();
//        List <PlayList> playlist = new ArrayList<>();
//        if (repo1.login(username, year)) {
//            PlayListImpl repo = new PlayListImpl();
//            //send the
//            playlist = repo.findAll();
//        }
//        return playlist;
//    }
//    //Prints specific playlist
//    @GET
//    @Path("{username}/{year}/playlists/{id}")
//    @Produces("application/json")
//    public PlayList getOne(@PathParam("id") Long id,
//                           @PathParam("username") String username,
//                           @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo1 = new UserImpl();
//        PlayList list = new PlayList();
//        if (repo1.login(username, year)) {
//            User user = repo1.findById(repo1.getID(username, year).getId());
//            PlayListImpl repo = new PlayListImpl();
//
//            for(int a = 0; a<user.getPlaylist().size(); a++){
//                if(user.getPlaylist().get(a).getId().compareTo(id)==0){
//                    list = user.getPlaylist().get(a);
//
//                }
//
//            }
//
//
//        }
//        return list;
//
//    }
//    //Deletes entire playlist
//    @DELETE
//    @Path("{username}/{year}/playlists/delete/{id}")
//    @Produces("application/json")
//    public String delete(@PathParam("id") Long id,
//                         @PathParam("username") String username,
//                         @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo1 = new UserImpl();
//        String delete = "unsuccessful";
//        if (repo1.login(username, year)) {
//            User user = repo1.findById(repo1.getID(username,year).getId());
//            PlayListImpl repo = new PlayListImpl();
//            for(PlayList x:user.getPlaylist())
//                if(Objects.equals(x.getId(), id))
//                     delete = repo.delete(id);
//
//        }
//        return delete;
//    }
//
//}
