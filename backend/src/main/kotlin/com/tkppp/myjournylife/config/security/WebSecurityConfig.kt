package com.tkppp.myjournylife.config.security

import com.tkppp.myjournylife.auth.auth_handler.CustomAuthenticationFailureHandler
import com.tkppp.myjournylife.auth.auth_handler.CustomAuthenticationSuccessHandler
import com.tkppp.myjournylife.auth.filter.JwtAuthorizationFilter
import com.tkppp.myjournylife.auth.service.CustomUserDetailsService
import com.tkppp.myjournylife.auth.util.RestAuthenticationEntryPoint
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

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/h2-console/**");
        super.configure(web)
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .csrf().ignoringAntMatchers("/h2-console/**").disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.formLogin()
            .loginProcessingUrl("/login")
            .usernameParameter("emailAddress")
            .passwordParameter("password")
            .successHandler(customAuthenticationSuccessHandler)
            .failureHandler(customAuthenticationFailureHandler)

        http.exceptionHandling()
            .authenticationEntryPoint(RestAuthenticationEntryPoint())

        http.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.authorizeRequests()
            .antMatchers(
                "/h2-console/**"    // 여기!
            ).permitAll()
            .antMatchers("/api/login/test").hasRole("MEMBER")
            .anyRequest().permitAll()

    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder)
    }
}