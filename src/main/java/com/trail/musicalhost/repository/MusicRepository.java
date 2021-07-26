package com.trail.musicalhost.repository;

import com.trail.musicalhost.model.Music;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;

public interface MusicRepository extends MongoRepository<Music,String> {

    @Query("select p from Music p where p.user.id=:id")
    List<Music> findAllMusicByUserId(Integer id);
}
