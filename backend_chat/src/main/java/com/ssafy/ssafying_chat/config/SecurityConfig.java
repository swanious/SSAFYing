package com.ssafy.ssafying_chat.config;

import com.ssafy.ssafying_chat.model.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }
	
    // 위에 언급한 인증 구조 중에 4번에 해당,
    // 여기서 DB 로부터 아이디, 비번이 맞는지 해당 유저가 어떤 권한을 가지는지를 체크한다.
    // UserDetailsService 인터페이스를 상속받은 클래스가 있다면 그 클래스에서 인증을 시도 하면된다. 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //실제 인증을 진행할 Provider
        auth.authenticationProvider(authenticationProvider(customUserDetailsService));
    }
    
    // @Override
    // public void configure(WebSecurity web) {
	//   //이미지,자바스크립트,css 디렉토리 보안 설정 
	// }

    // 이 메소드에서 URL 별 권한 설정, 로그인,세션 등등 HTTP 보안 관련 설정을 해주면 된다.
    @Override
	protected void configure(HttpSecurity http) throws Exception{
		http
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                    .and()
                .csrf().disable()
                .headers()
                .frameOptions().sameOrigin()
                    .and()
                .authorizeRequests()
                .antMatchers("/ws-stomp/**").permitAll()
                .anyRequest().authenticated()
                    .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://i4c101.p.ssafy.io");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        // configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}