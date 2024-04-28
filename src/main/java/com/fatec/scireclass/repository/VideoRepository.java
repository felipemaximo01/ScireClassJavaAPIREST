package com.fatec.scireclass.repository;

import com.fatec.scireclass.model.Aula;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.scireclass.model.Video;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {

    List<Video> findAllByAula(Aula aula);
}
