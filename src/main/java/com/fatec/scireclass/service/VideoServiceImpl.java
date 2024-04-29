package com.fatec.scireclass.service;

import com.fatec.scireclass.model.Aula;
import com.fatec.scireclass.model.Video;
import com.fatec.scireclass.model.dto.VideoDTO;
import com.fatec.scireclass.model.mapper.VideoMapper;
import com.fatec.scireclass.repository.AulaRepository;
import com.fatec.scireclass.repository.VideoRepository;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    AulaRepository aulaRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    private AzureBlobStorageService azureBlobStorageService;

    @Override
    public VideoDTO addVideo(String aulaId, MultipartFile file) throws IOException, InterruptedException {
        Aula aula = aulaRepository.findById(aulaId).get();
        if(aula == null)
            throw new ResourceNotFoundException("Não foi possível encontrar uma aula");
        String path = azureBlobStorageService.write(file,"curso/"+aula.getCurso().getId()+"/"+aula.getId()+"/"+UUID.randomUUID().toString()+file.getOriginalFilename());
        Video video = new Video();
        video.setAula(aula);
        video.setPath(path);
        video.setTitle(file.getOriginalFilename());
        video.setDurationInMinutes(getVideoDurationInMinutes(file));
        video = videoRepository.save(video);
        aula.setVideo(video);
        aulaRepository.save(aula);
        return VideoMapper.videoToVideoDTO(video);
    }

    @Override
    public VideoDTO getVideo(String videoId) {
        Video video = videoRepository.findById(videoId).get();
        if(video == null)
            throw new ResourceNotFoundException("Video não foi encontrado");
        return VideoMapper.videoToVideoDTO(video);
    }

    @Override
    public List<VideoDTO> getAllVideos(String aulaId) {
        Aula aula = aulaRepository.findById(aulaId).get();
        if(aula == null)
            throw new ResourceNotFoundException("Não foi possível encontrar uma aula");

        List<Video> videos = videoRepository.findAllByAula(aula);
        List<VideoDTO> videosDTO = new ArrayList<>();
        for (Video video : videos) {
            videosDTO.add(VideoMapper.videoToVideoDTO(video));
        }
        return videosDTO;
    }

    @Override
    public void deleteVideo(String videoId, String path) {
        videoRepository.deleteById(videoId);
        azureBlobStorageService.delete(path);
    }

    @Override
    public byte[] getFile(String path) {
        return new byte[0];
    }

    @Override
    public int getVideoDurationInMinutes(MultipartFile videoFile) throws IOException, InterruptedException {
        File tempFile = File.createTempFile("temp","."+FilenameUtils.getExtension(videoFile.getOriginalFilename()));
        videoFile.transferTo(tempFile);
        String[] command = {"ffmpeg", "-i", tempFile.getAbsolutePath(), "-show_entries", "format=duration", "-v", "quiet", "-of", "csv=p=0"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        process.waitFor();
        String output = new String(process.getInputStream().readAllBytes());
        double durationInSeconds = Double.parseDouble(output.trim());
        int durationInMinutes = (int) Math.round(durationInSeconds / 60);
        tempFile.delete();
        return durationInMinutes;
    }
}
