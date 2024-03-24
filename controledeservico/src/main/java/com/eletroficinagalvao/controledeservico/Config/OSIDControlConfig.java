package com.eletroficinagalvao.controledeservico.Config;

import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.List;

@Configuration
@Log4j2
public class OSIDControlConfig {

    @Autowired
    private OSRepository osRepository;

    public static int idAtual;

    @Bean
    public OSIDControlConfig setarControleIdOS() {
        try{
            List<OS> os = osRepository.findAll();

            os.sort(Comparator.comparingInt(OS::getId));

            idAtual = (os == null || os.isEmpty()) ? 1 : os.get(os.size() - 1).getId() + 1;
            log.info("Controle de ID das OS: id atual -> {}", idAtual);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
