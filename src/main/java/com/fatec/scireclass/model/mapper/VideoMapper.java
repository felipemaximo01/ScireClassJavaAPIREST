package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Imagem;
import com.fatec.scireclass.model.Video;
import com.fatec.scireclass.model.dto.ImagemDTO;
import com.fatec.scireclass.model.dto.VideoDTO;

public class VideoMapper {
    private VideoMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Video videoDTOToVideo(VideoDTO videoDTO){
        Video video = new Video();
        if(videoDTO.getTitle() != null)
            video.setTitle(videoDTO.getTitle());
        if(videoDTO.getPath() != null)
            video.setPath(videoDTO.getPath());
        if(videoDTO.getDurationInMinutes() != null)
            video.setDurationInMinutes(videoDTO.getDurationInMinutes());
        return video;
    }

    public static VideoDTO videoToVideoDTO(Video video){
        VideoDTO videoDTO = new VideoDTO();
        if(video.getTitle() != null)
            videoDTO.setTitle(video.getTitle());
        if(video.getPath() != null)
            videoDTO.setPath(video.getPath());
        if(video.getId() != null)
            videoDTO.setId(video.getId());
        if(video.getDurationInMinutes() != null)
            videoDTO.setDurationInMinutes(video.getDurationInMinutes());
        return videoDTO;
    }
}
