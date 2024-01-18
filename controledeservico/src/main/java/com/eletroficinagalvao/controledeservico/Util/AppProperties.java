package com.eletroficinagalvao.controledeservico.Util;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Component
@Log4j2
public class AppProperties {
    private static final Path propertyFilePath = Paths.get("").toAbsolutePath().getParent();

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(new File(propertyFilePath + "/app_data/app.properties")));
        } catch (IOException e) {
            log.error("Arquivo de propriedades não encontrado");
        }
    }

    public static String get(String key){
        return (String) properties.get(key);
    }

}
