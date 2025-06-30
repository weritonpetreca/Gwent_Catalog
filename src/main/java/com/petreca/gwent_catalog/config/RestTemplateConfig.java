package com.petreca.gwent_catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
/*
Indica que a classe RestTemplateConfig é uma classe de configuração do Spring.
Classes anotadas com @Configuration podem conter métodos anotados com @Bean que definem beans gerenciados pelo Spring.
 */
public class RestTemplateConfig {

    @Bean
    /*
    Marca o metodo restTemplate() como um produtor de um bean gerenciado pelo Spring.

    O objeto retornado por esse metodo (RestTemplate) estará disponível para injeção automática (@Autowired) em outros componentes da aplicação.
     */
    public RestTemplate restTemplate() {
        /*
        É uma classe do Spring usada para fazer requisições HTTP de forma simples (GET, POST, PUT, DELETE) para APIs REST.

        Com esse bean configurado, é possível injetar o RestTemplate em qualquer componente Spring e usá-lo para consumir APIs externas.
         */

        return new RestTemplate();
    }
}
