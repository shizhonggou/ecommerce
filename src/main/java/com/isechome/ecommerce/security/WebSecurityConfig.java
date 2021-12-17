package com.isechome.ecommerce.security;

import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.security.handler.SthAuthenticationFailureHandler;
import com.isechome.ecommerce.security.handler.SthAuthenticationSuccessHandler;
import com.isechome.ecommerce.security.handler.SthLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.Locale;

/**
 * @Author: libing
 * @Date: 2021/4/13
 * @Description: spring-security权限管理的核心配置
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private SthFilterInvocationSecurityMetadataSource filterMetdataSource;

    @Autowired
    private SthAccessDecisionManager accessDecisionManager;

    @Autowired
    @Qualifier("isechomeMybatisDataSource")
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                            return MD5Util.encode((String)charSequence );
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        
                        if(charSequence.length() <= 16){
                            String es = MD5Util.encode((String) charSequence);
                            return s.toUpperCase(Locale.ROOT).equals(es);
                           }
                           else{
                          //  return s.toUpperCase(Locale.ROOT).equals(charSequence);
                           return s.equals(charSequence);
                           }
                        
                    }
                });
               
    }

     @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/**/*.css", "/**/*.js");
    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        //启动时创建一张表，这个参数到第二次启动时必须注释掉，因为已经创建了一张表
//        jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        return jdbcTokenRepositoryImpl;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                //.csrf().ignoringAntMatchers("/elasticsearch/**","/druid/*")
                .csrf()
                .and().authorizeRequests()
                .antMatchers("/purchase_car/**","/resourcemanage/**","/pieceweight/**","/orderinformation/**","/warehouse_resource/**","/shelf_resource/**","/scsteelmill/**","/baseprice/**","/scpieceweightmanage/**","/transet/**","/settings/**","/scorderlist/**","/shipmentsimport/**","/khmanage/**","/fpmanage/**","/fhmanage/**","/job/**").authenticated()
                //.antMatchers("/index","index/").permitAll()
                .antMatchers("/company/**","/userInfo/**","/financial/**").authenticated()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setSecurityMetadataSource(filterMetdataSource);
//                        o.setAccessDecisionManager(accessDecisionManager);
//                        return o;
//                    }
//
//                })
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login_p").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(new SthAuthenticationFailureHandler())
                .successHandler(new SthAuthenticationSuccessHandler())
                .permitAll()
//                .and()
//                .sessionManagement()
//                .invalidSessionUrl("/login_p")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredSessionStrategy()
                .and()
                .logout()
                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/")
                    .logoutSuccessHandler(new SthLogoutSuccessHandler())
                    .deleteCookies("SESSION")
                    .deleteCookies("remember-me")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()

                .and()
                    .rememberMe()
                    .tokenValiditySeconds(604800)
                    .tokenRepository(getPersistentTokenRepository())
                ;
        //无权限出错页面
        http.exceptionHandling().accessDeniedPage("/page403");
    }
}
