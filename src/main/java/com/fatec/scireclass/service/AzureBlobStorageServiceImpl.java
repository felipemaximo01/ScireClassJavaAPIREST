package com.fatec.scireclass.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;

public class AzureBlobStorageServiceImpl implements AzureBlobStorageService{

    @Autowired
    private BlobContainerClient blobContainerClient;


    @Override
    public String write(MultipartFile multipartFile, String path) {
        BlobClient blobClient = blobContainerClient.getBlobClient(path);
        try {
            blobClient.upload(multipartFile.getInputStream(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    public byte[] read(String path) {
        BlobClient blobClient = blobContainerClient.getBlobClient(path);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.downloadStream(outputStream);
        final byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

    @Override
    public void delete(String path) {
        BlobClient client = blobContainerClient.getBlobClient(path);
        client.delete();
    }

    
}
