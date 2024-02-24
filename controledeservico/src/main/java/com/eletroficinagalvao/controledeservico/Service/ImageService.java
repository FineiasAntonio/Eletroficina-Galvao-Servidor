package com.eletroficinagalvao.controledeservico.Service;

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
import java.util.List;
import java.util.Random;

@Service
public class ImageService {

    public static final int ENTRANCE_METHOD = 1;
    public static final int EXIT_METHOD = 2;

    @Getter
    private String method;
    private String diretorioUsuario;

    private Random random = new Random();

    @Autowired
    private OSRepository repository;

    public String uploadImage(int id, List<MultipartFile> imagens, int method) {
        setMethod(method);

        try {
            InputStream credentialsStream = ImageService.class.getClassLoader().getResourceAsStream("eletroficina-galvao-storage-firebase-adminsdk-31szq-ab6423537a.json");
            Credentials credentials = GoogleCredentials.fromStream(credentialsStream);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();



            byte[] buffer = new byte[4096];
            int bytesRead;

            for (MultipartFile e : imagens) {

                File tempFile = new File(String.format("%d_%d",id, random.nextInt(1000, 9999)));

                InputStream inputStream = e.getInputStream();
                OutputStream outputStream = new FileOutputStream(tempFile);


                while ((bytesRead = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();


                BlobId blobId = BlobId.of("eletroficina-galvao-storage.appspot.com",
                        String.format("Imagens/%d/%s/%s", id, this.method, tempFile.getName().concat(e.getOriginalFilename().substring(e.getOriginalFilename().lastIndexOf(".")))));
                //TODO: reconhecer o jpg
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build();
                storage.create(blobInfo, Files.readAllBytes(tempFile.toPath()));

                //TODO: ajeitar a url
                String URL = "https://firebasestorage.googleapis.com/v0/b/eletroficina-galvao-storage.appspot.com/o/%s?alt=media".formatted(URLEncoder.encode(tempFile.getName(), StandardCharsets.UTF_8));
                tempFile.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: retornar a url da pasta generalizada
        return "aa";
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

    // Depois
//    public File getImageById(int id) {
//        File zipTemp = new File("imagens_%d.zip".formatted(id));
//        try {
//            verify(id);
//
//            zipTemp.createNewFile();
//
//            List<File> fotos = Arrays.stream(Path.of(diretorioUsuario)
//                            .toFile()
//                            .listFiles())
//                    .toList();
//
//            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipTemp));
//            for (File e : fotos) {
//                try (InputStream inputStream = new FileInputStream(e)) {
//
//                    byte[] buffer = new byte[8192];
//                    int bytesRead;
//
//                    zipOutputStream.putNextEntry(new ZipEntry(e.getName()));
//
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        zipOutputStream.write(buffer, 0, bytesRead);
//                    }
//
//                }
//            }
//            zipOutputStream.close();
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (SQLDataException ex) {
//            ex.printStackTrace();
//        }
//        return zipTemp;
//    }

    public void delete(int id) {
    }
}
