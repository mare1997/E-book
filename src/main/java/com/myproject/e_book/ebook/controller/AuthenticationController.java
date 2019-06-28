package com.myproject.e_book.ebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.myproject.e_book.ebook.security.RestAuthenticationEntryPoint;
import com.myproject.e_book.ebook.security.TokenAuthenticationFilter;
import com.myproject.e_book.ebook.security.TokenHelper;
import com.myproject.e_book.ebook.services.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    //Neautorizovani pristup zastcenim resursima
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //Definisemo nacin autentifikacije
    //Svaki
    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( jwtUserDetailsService )
                .passwordEncoder( passwordEncoder() );
    }

    @Autowired
    TokenHelper tokenHelper;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //komunikacija izmedju klijenta i servera je stateless
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                //za neautorizovane zahteve posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
                .authorizeRequests()
                //svim korisnicima dopusti da pristupe putanjama /auth/**
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/ebook/books/all").permitAll()
                .antMatchers("/ebook/books/all").permitAll()
                .antMatchers("/ebook/books/ebooksbycategory/**").permitAll()
                .antMatchers("/ebook/category").permitAll()
                .antMatchers("/ebook/languages/**").permitAll()
                .antMatchers("/ebook/users").permitAll()
                .antMatchers("/ebook/users/get/**").permitAll()
                .antMatchers("/ebook/users/**").hasAuthority("ADMIN")
                .antMatchers("/ebook/category/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/ebook/search/**").permitAll()
                .antMatchers("/ebook/books/**").hasAuthority("ADMIN")
                //svaki zahtev mora biti autorizovan
                .anyRequest().authenticated().and()
                //presretni svaki zahtev filterom
                .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService), BasicAuthenticationFilter.class)

        .csrf().disable();
    }



    //Generalna bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/auth/login",
                "/ebook/search"
                
        );
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/webjars/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/ebook/books/all",
                "/ebook/books/**",
                "/ebook/category"
                
            );
//        "/**/*.jpg"
    }

}
