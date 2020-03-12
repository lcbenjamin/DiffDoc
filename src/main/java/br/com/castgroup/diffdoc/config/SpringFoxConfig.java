package br.com.castgroup.diffdoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import br.com.castgroup.diffdoc.dao.DocumentDAO;
import br.com.castgroup.diffdoc.service.DocumentRepository;
import br.com.castgroup.diffdoc.service.DocumentService;
import br.com.castgroup.diffdoc.service.impl.DocumentRepositoryImpl;
import br.com.castgroup.diffdoc.service.impl.DocumentServiceImpl;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {   
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
          .paths(PathSelectors.any())
          .build();                                           
    }
   
}
