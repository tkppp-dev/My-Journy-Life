package com.tkppp.myjournylife.config.security

import com.tkppp.myjournylife.auth.auth_handler.CustomAuthenticationFailureHandler
import com.tkppp.myjournylife.auth.auth_handler.CustomAuthenticationSuccessHandler
import com.tkppp.myjournylife.auth.filter.JwtAuthorizationFilter
import com.tkppp.myjournylife.auth.service.CustomUserDetailsService
import com.tkppp.myjournylife.auth.filter.RestAuthenticationEntryPoint
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
    private val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtAuthorizationFilter: JwtAuthorizationFilter
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.addAllowedOrigin("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/h2-console/**")
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .cors().and()
            .csrf().ignoringAntMatchers("/h2-console/**").disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.formLogin()
            .loginProcessingUrl("/api/login")
            .usernameParameter("emailAddress")
            .passwordParameter("password")
            .successHandler(customAuthenticationSuccessHandler)
            .failureHandler(customAuthenticationFailureHandler)

        http.exceptionHandling()
            .authenticationEntryPoint(RestAuthenticationEntryPoint())

        http.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/api/login/**", "/api/register/**").permitAll()
            .anyRequest().hasRole("MEMBER")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder)
    }
}