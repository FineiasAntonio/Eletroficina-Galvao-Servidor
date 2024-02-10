package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;
import com.eletroficinagalvao.controledeservico.Util.AppProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLDataException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ImageService {

    public static final int ENTRANCE_METHOD = 1;
    public static final int EXIT_METHOD = 2;

    @Getter
    private String method;
    private String diretorioUsuario;

    private Random random = new Random();

    //private static String diretorio = "C:/Users/%s/Pictures".formatted(System.getProperty("user.name"));
    private static String diretorio = AppProperties.get("storage.path");

    @Autowired
    private OSRepository repository;

    public String readImage(int id, List<MultipartFile> imagens, int method) {
        setMethod(method);

        try {

            diretorioUsuario = Files.createDirectories(Path.of(diretorio + "\\OrdensDeServicos\\ImagensOS\\%d\\%s".formatted(id, getMethod())))
                    .toString();

            byte[] buffer = new byte[4096];
            int bytesRead;

            for(MultipartFile e: imagens) {

                try (
                        InputStream inputStream = e.getInputStream();
                        OutputStream outputStream = new FileOutputStream(new File(diretorioUsuario + "\\%d_%d.jpg".formatted(id, random.nextInt(1000, 9999))))
                ) {

                    while ((bytesRead = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return diretorioUsuario;
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

    public void delete(int id){
        try {
            String a =  diretorio + "/OrdensDeServicos/ImagensOS/%s".formatted(id);

            Files.delete(Path.of(a));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
