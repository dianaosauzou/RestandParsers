package com.example.entities;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name ="Track.findAll",
                query = "select c FROM Track c"),
        @NamedQuery(name ="Track.findByTrackID",
                query = "select c FROM Track c WHERE c.trackId = :trackId")})
public class Track {

    @Id
    @Column(name = "id", nullable = false)
    private Long trackId;

    private String name;
    private String artist;
    private String genre;

    public Track(Long trackId, String name, String artist, String genre) {
        this.trackId = trackId;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
    }

    public Track(){

    }

    public Track(Long trackID) {
        this.trackId = trackID;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
