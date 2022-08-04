package com.expertpeople.infra.config;

import com.expertpeople.infra.jwt.JwtAuthenticationEntryPoint;
import com.expertpeople.infra.jwt.JwtRequestFilter;
import com.expertpeople.modules.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers(
//
//                );
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/").permitAll()
//                .anyRequest().authenticated();
//    }
    private final AccountService accountService;
    private final DataSource dataSource;

    private final UserDetailsService jwtUserDetails;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)throws Exception{
        authenticationManagerBuilder.userDetailsService(jwtUserDetails);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // For CORS error
        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/login").permitAll().
                // all other requests need to be authenticated
                        anyRequest().authenticated();
        // stateless session exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.authorizeRequests()
//                .mvcMatchers("/", "/login", "/join-up").permitAll()
//                .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
//                .mvcMatchers("/favicon.ico", "/resources/**", "/error").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/")
////                .and()
////                .rememberMe().userDetailsService(accountService).tokenRepository(tokenRepository())
//                .and().build();
//    }
//    @Bean
//    public PersistentTokenRepository tokenRepository() {
//        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//        return jdbcTokenRepository;
//
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
//        return configuration.getAuthenticationManager();
//    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .mvcMatchers("/node_modules/**")
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
}
