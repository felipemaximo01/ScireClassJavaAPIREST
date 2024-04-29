package com.fatec.scireclass.service;


import com.fatec.scireclass.model.dto.VideoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface VideoService {
    VideoDTO addVideo(String aulaId, MultipartFile file) throws IOException, InterruptedException;
    VideoDTO getVideo(String videoId);
    List<VideoDTO> getAllVideos(String aulaId);
    void deleteVideo(String videoId, String path);
    byte[] getFile(String path);
    Double getVideoDurationInMinutes(MultipartFile file) throws IOException, InterruptedException;
}