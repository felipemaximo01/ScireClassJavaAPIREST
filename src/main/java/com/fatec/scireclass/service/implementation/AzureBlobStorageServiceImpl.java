package com.fatec.scireclass.service.implementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fatec.scireclass.service.AzureBlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;

@Service
public class AzureBlobStorageServiceImpl implements AzureBlobStorageService {

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
    public String write(InputStream inputStream, String path) {
        BlobClient blobClient = blobContainerClient.getBlobClient(path);
        blobClient.upload(inputStream,false);
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
