package com.phamvantoan.webBanSachBackend.config;

import com.phamvantoan.webBanSachBackend.service.JwtFilter;
import com.phamvantoan.webBanSachBackend.service.userService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

@Configuration
public class securityConfiguration implements RepositoryRestConfigurer{
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private EntityManager entityManager;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ //mã hóa password
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Autowired
    public DaoAuthenticationProvider daoAuthenticationProvider(userService userservice){
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userservice);    //set User để spring nhận diện từ interface userService đã tạo
        dap.setPasswordEncoder(passwordEncoder()); //set cách mã hóa password
        return dap;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{    //phân quyền
        http.authorizeHttpRequests(
                config->config
                        .requestMatchers(HttpMethod.GET, endpoints.PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST,endpoints.PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, endpoints.ADMIN_GET_ENDPOINTS).hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, endpoints.ADMIN_POST_ENDPOINTS).hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, endpoints.STAFF_GET_ENDPOINT).hasAnyAuthority("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST, endpoints.STAFF_POST_ENDPOINT).hasAnyAuthority("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT, endpoints.STAFF_PUT_ENDPOINT).hasAnyAuthority("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE, endpoints.STAFF_DELETE_ENDPOINT).hasAnyAuthority("ADMIN", "STAFF")

        );
        http.cors(cors->{ //set endpoint của frontend và các method được phép truy cập
            cors.configurationSource(
                    request -> {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.addAllowedOrigin(endpoints.FRONT_END_HOST);
                        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                        corsConfiguration.addAllowedHeader("*");
                        return corsConfiguration;
                    }
            );
        });
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  //filter lấy ra userdetails và token trước khi phân quyền khi gọi endpoint
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());
        return http.build();
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //yêu cầu spring data rest hiển thị ID của các đối tượng trong phản hồi json
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));

//        cors.addMapping("/**").allowedOrigins(url).allowedMethods("GET", "POST", "PUT", "DELETE");  //cho phép truy cập end point từ frontend

        HttpMethod[] deleteMethod = {HttpMethod.DELETE};
        HttpMethod[] getMethod = {HttpMethod.GET};
        HttpMethod[] postMethod = {HttpMethod.POST};
        HttpMethod[] putMethod = {HttpMethod.PUT};
    }

    private void disableMethod(Class c, RepositoryRestConfiguration config, HttpMethod[] methods){  // phương thức dùng để chặn các truy cập đến entity
        config.getExposureConfiguration().forDomainType(c).withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods)).withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(methods));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
