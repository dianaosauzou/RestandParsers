package com.example.entities;

import javax.persistence.*;
import java.util.List;
@Entity
@NamedQuery(name ="PlayList.findAll",
        query = "select c FROM PlayList c")
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @JoinTable(name = "PlayList_tracks",
            joinColumns = @JoinColumn(name = "PlayList_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tracks_id", referencedColumnName = "id"))
    //you cant merge because the ids are not the same!!
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Track> tracks;

    //somehow i need to say if the track on the playlist matches the track on the database we shjould merge

    public PlayList(String name, List<Track> list) {
        this.name = name;
        this.tracks = list;
    }

    public PlayList() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
