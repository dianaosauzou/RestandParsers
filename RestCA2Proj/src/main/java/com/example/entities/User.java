package com.example.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name ="User.findAll",
        query = "select c FROM User c")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String email;
    private String libraryID;
    private String username;
    private int joinDate;
    private String country;
    private boolean auth;

    @OneToMany( orphanRemoval = true)
    private List <PlayList> playlist;

    @OneToMany
    private List <Track> tracks;

    @OneToMany
    private List <Reccomendations> recSongs;


    public User(String name, String email,String libraryID, String username, int joinDate, String country, boolean auth) {
        this.name = name;
        this.email = email;
        this.libraryID = libraryID;
        this.username = username;
        this.joinDate = joinDate;
        this.country = country;
        this.auth = auth;

        //user should have a list of tracks, list of playlists?voice
    }

    public String getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(String libraryID) {
        this.libraryID = libraryID;
    }


    public List<Reccomendations> getRecSongs() {
        return recSongs;
    }

    public void setRecSongs(List<Reccomendations> recSongs) {
        this.recSongs = recSongs;
    }

    public User() {

    }

    public void setPlaylist(List<PlayList> playlist) {
        this.playlist = playlist;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List <PlayList> getPlaylist() {
        return playlist;
    }


    public List<Track> getTracks() {
        return tracks;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(int joinDate) {
        this.joinDate = joinDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//public String toString(){
//        return name;
//
//}
}
