package com.shop.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityWEBConfig extends WebSecurityConfigurerAdapter {


    private DataSource dataSource;

    @Autowired
    @Qualifier("dataBaseShop")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email , password, enabled FROM user WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT u.email as username, ur.role" +
                        "FROM user u, user_role ur" +
                        "WHERE u.email = ? AND u.id_user = ur.id_user")
                .passwordEncoder(passwordEncoder());
    }


    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/admin/add/product").hasAuthority("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password");
    }
}