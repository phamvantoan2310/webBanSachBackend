package com.phamvantoan.webBanSachBackend.config;

import com.phamvantoan.webBanSachBackend.entity.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class methodRestConfig implements RepositoryRestConfigurer {
    @Autowired
    private EntityManager entityManager;
    private String url = "http://localhost:8080";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //expose id in json
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));

        HttpMethod[] deleteMethod = {HttpMethod.DELETE};
        HttpMethod[] getMethod = {HttpMethod.GET};
        HttpMethod[] postMethod = {HttpMethod.POST};
        HttpMethod[] putMethod = {HttpMethod.PUT};

        disableMethod(Author.class, config, deleteMethod);
    }

    private void disableMethod(Class c, RepositoryRestConfiguration config, HttpMethod[] methods){
        config.getExposureConfiguration().forDomainType(c).withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods)).withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(methods));
    }
}
