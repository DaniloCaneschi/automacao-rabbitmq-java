package com.automacaoifet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  UsuarioDetailService usuarioDetailService;

  @Bean
  public WebMvcConfigurer corsConfigure(){
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("OPTIONS", "GET", "PUT", "POST", "DELETE");
      }
    };
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http
            .cors()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests().antMatchers(HttpMethod.GET, "/usuario/atual").hasAnyRole("ADMIN", "USER")
            .and().authorizeRequests().antMatchers(HttpMethod.POST, "/usuario").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            .and().headers().cacheControl().disable()
            .and().httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(usuarioDetailService)
            .passwordEncoder(new BCryptPasswordEncoder());
  }
}
