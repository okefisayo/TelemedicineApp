package xyz.cglzwz.chatroom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.cglzwz.chatroom.service.CustomUserService;

/**
 * Spring Security 5 config
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     @Bean
     UserDetailsService myCustomUserService() {
     return new CustomUserService();
     }

     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(myCustomUserService());//myCustomUserService()
     }


     @Override
     public void configure(WebSecurity web) throws Exception {
        super.configure(web);
     }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 授权请求
                .authorizeRequests()
                // 不拦截 "/login"
                .antMatchers("/login").permitAll()
                // 首页需要认证
                .antMatchers("/", "/mainpage").authenticated()//"/index",
                .and()
                .formLogin()
                // 自定义登录页面,而且要保证提交表单post的请求也是这个。。。（自己的）
                .loginPage("/login").permitAll()
                .successForwardUrl("/")
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
        http.sessionManagement().maximumSessions(1);
    }
}
