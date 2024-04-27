package com.fatec.scireclass.service;

import org.springframework.web.multipart.MultipartFile;

public interface AzureBlobStorageService {
    public String write(MultipartFile multipartFile, String path);
    public byte[] read(String path);
    public void delete(String path);
}
