package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Exception.InternalServerErrorException;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class ImageService {

    public static final int ENTRANCE_METHOD = 1;
    public static final int EXIT_METHOD = 2;

    @Getter
    private String method;

    private final Random random = new Random();

    public List<String> uploadImage(int id, List<MultipartFile> imagens, int method) {
        setMethod(method);

        List<String> images = new LinkedList<>();
        try {
            InputStream credentialsStream = ImageService.class.getClassLoader().getResourceAsStream("eletroficina-galvao-storage-firebase-adminsdk-31szq-ab6423537a.json");
            Credentials credentials = GoogleCredentials.fromStream(credentialsStream);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            byte[] buffer = new byte[4096];
            int bytesRead;

            for (MultipartFile e : imagens) {

                File tempFile = new File(String.format("%d_%d", id, random.nextInt(1000, 9999)));

                InputStream inputStream = e.getInputStream();
                OutputStream outputStream = new FileOutputStream(tempFile);


                while ((bytesRead = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();


                String filePath = String.format("Imagens/%d/%s/%s", id, this.method, tempFile.getName().concat(e.getOriginalFilename().substring(e.getOriginalFilename().lastIndexOf("."))));

                BlobId blobId = BlobId.of("eletroficina-galvao-storage.appspot.com", filePath);
                BlobInfo blobInfo = getExtensionFromBlob(blobId);
                storage.create(blobInfo, Files.readAllBytes(tempFile.toPath()));

                inputStream.close();
                outputStream.close();
                storage.close();

                images.add("https://firebasestorage.googleapis.com/v0/b/eletroficina-galvao-storage.appspot.com/o/%s?alt=media"
                        .formatted(filePath.replace("/", "%2F")));

                Files.delete(tempFile.toPath());
            }
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return images;
    }

    private BlobInfo getExtensionFromBlob(BlobId blobId){
        String fileExtension = blobId.getName().substring(blobId.getName().lastIndexOf("."));

        if(fileExtension.contains("jpg")){
            return BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        }
        return BlobInfo.newBuilder(blobId).setContentType("image/png").build();
    }

    private void setMethod(int method) {
        if (method == ENTRANCE_METHOD) {
            this.method = "Entrada";
        } else if (method == EXIT_METHOD) {
            this.method = "Saida";
        } else {
            throw new IllegalArgumentException("Método inválido");
        }
    }

}
