package com.eletroficinagalvao.controledeservico.Config;

import com.eletroficinagalvao.controledeservico.Util.AppProperties;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PropertiesConfiguration {

    @Bean
    @Scope (value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AppProperties configPropertiesClass(AppProperties appProperties){
        return new AppProperties();
    }

}
