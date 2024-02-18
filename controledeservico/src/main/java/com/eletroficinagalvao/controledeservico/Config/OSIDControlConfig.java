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

    @Bean
    public OSIDControlConfig setarControleIdOS(){
        List<OS> os = osRepository.findAll();

        os.sort(Comparator.comparingInt(OS::getId));

        int idAtual = os.get(os.size() - 1).getId() + 1;
        log.info("Controle de ID das OS: id atual -> {}",idAtual);
        OS.setControleId(idAtual);
        return null;
    }
}