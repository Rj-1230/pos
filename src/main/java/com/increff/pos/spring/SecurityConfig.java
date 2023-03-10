package com.increff.pos.spring;

import com.increff.pos.handler.CustomAccessDeniedHandler;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static Logger logger = Logger.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http//
                // Match only these URLs
                .requestMatchers()//
                .antMatchers("/api/**")//
                .antMatchers("/ui/**")//
                .and().authorizeRequests()//
                .antMatchers("/api/supervisor/**").hasAuthority("supervisor")//
                .antMatchers("/api/**").hasAnyAuthority("supervisor", "operator")//
                .antMatchers("/ui/supervisor/**").hasAuthority("supervisor")//
                .antMatchers("/ui/**").hasAnyAuthority("supervisor", "operator")//
                .and()//
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())//
                // Ignore CSRF and CORS
                .and().csrf().disable().cors().disable();
        logger.info("Configuration complete");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}