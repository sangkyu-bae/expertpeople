package com.expertpeople.infra.config;

import com.expertpeople.infra.jwt.JwtAuthenticationEntryPoint;
import com.expertpeople.infra.jwt.JwtRequestFilter;
import com.expertpeople.infra.jwt.JwtTokenProvider;
import com.expertpeople.modules.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)throws Exception{
//        authenticationManagerBuilder.userDetailsService(jwtUserDetails);
//    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
     http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 X
                .and()
                .authorizeRequests()
                .antMatchers("/join", "/login","/","/index","/swagger-ui.html","/api/join-up","/test").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        // We don't need CSRF for this example
//        httpSecurity.csrf().disable()
//                // dont authenticate this particular request
//                .authorizeRequests().antMatchers("/join", "/login","/","/index","/swagger-ui.html","/api/join-up","/test").permitAll().
//                // all other requests need to be authenticated
//                        anyRequest().authenticated().and().
//                // make sure we use stateless session; session won't be used to
//                // store user's state.
//                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // Add a filter to validate the tokens with every request
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .mvcMatchers("/webjars/**")
                .mvcMatchers("/swagger-resources/**/**")
                .mvcMatchers("/v2/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
//    @Bean
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
////        // For CORS error
////        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
////        // We don't need CSRF for this example
////        httpSecurity.csrf().disable()
////                // dont authenticate this particular request
////                .authorizeRequests().antMatchers("/login","swagger-ui/index.html","/").permitAll().
////                // all other requests need to be authenticated
////                        anyRequest().authenticated();
////         stateless session exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
////
////        // Add a filter to validate the tokens with every request
////        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        httpSecurity
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/","/index")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
//
//        httpSecurity.antMatcher("/api").addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }


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
//
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
