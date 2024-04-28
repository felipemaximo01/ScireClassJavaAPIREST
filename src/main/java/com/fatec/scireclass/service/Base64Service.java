package com.fatec.scireclass.service;

import java.io.*;
import java.util.Base64;

public class Base64Service {

    private Base64Service() {
        throw new IllegalStateException("Utility class");
      }
    

    public static InputStream base64ToInputStream(String base64) throws IOException {
        if(base64.contains(",")) {
            base64 = base64.split(",")[1];
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        return new ByteArrayInputStream(bytes);
    }

}
