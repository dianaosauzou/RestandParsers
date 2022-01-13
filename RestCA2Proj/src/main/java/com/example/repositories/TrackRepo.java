package com.example.repositories;

import com.example.entities.Track;

import java.util.List;

public interface TrackRepo {
    List<Track> findAll();
    Track findById(Long id);
    String delete(Long id);
    Track findByTrackId(Long id);
//    void createTrack();
}
