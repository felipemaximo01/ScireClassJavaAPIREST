package com.fatec.scireclass.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Video;

@Service
public interface VideoService {

    String addVideo(String title, String cursoID, MultipartFile file) throws IOException;

    Video getVideo(String id) throws IOException;

}