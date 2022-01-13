package com.example.repositories;

import com.example.entities.PlayList;

import java.util.List;

public interface PlayListRepo {

    List<PlayList> findAll();
    PlayList findById(Long id);
    String delete(Long id);

    //copy and move method
//    void createPlayList();
//    void moveTrack();


}
