package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.scireclass.model.Video;

public interface VideoRepository extends MongoRepository<Video, String> {
    
}
