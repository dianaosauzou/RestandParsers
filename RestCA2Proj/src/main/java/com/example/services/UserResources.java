package com.example.services;

import com.example.entities.PlayList;
import com.example.entities.Reccomendations;
import com.example.entities.Track;
import com.example.entities.User;
import com.example.implementations.PlayListImpl;
import com.example.implementations.RecsImpl;
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
public class UserResources {

    //Maybe have an auth key, if auth is true then else sorry you are not logged in.
//    @GET
//    @Path("/users")
//    @Produces("application/json")
//    public List<User> getAll() throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo = new UserImpl();
//        return  repo.findAll();
//    }
//    @GET
//    @Path("/users/{id}")
//    @Produces("application/json")
//    public User getOne(@PathParam("id") Long id) throws ParserConfigurationException, IOException, SAXException {
//        UserImpl repo = new UserImpl();
//        return  repo.findById(id);
//    }

    //Put username and year in as parameters before each thing
    @GET
    @Path("{username}/{year}")
    @Produces("application/json")
    public Boolean getUserVerified(
            @PathParam("username") String username,
            @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException {
        String user = username;
        int joinYear = year;
        UserImpl repo = new UserImpl();
        return repo.login(user, joinYear);
    }

    //Parses the logged in users information to the databse
    @POST
    @Path("{username}/{year}")
    @Produces("application/json")
    public String load(@PathParam("username") String username,
                       @PathParam("year") int year) throws ParserConfigurationException, IOException, SAXException, ParseException {
        UserImpl repo1 = new UserImpl();
        String status = "Upload failed";
        if (repo1.login(username, year) == true) {
            Long id = repo1.getID(username, year).getId();
            User user = repo1.findById(id);
            status = repo1.loadSystem(id, user);

        }
        return status;
    }





    //edit tracks and playlist
}
