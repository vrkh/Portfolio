package ru.isu.portfolio.security;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
public class SecurityConfig {
//    @Autowired
//    CustomUserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthEntryPointJwt unauthorizedHandler;
//
//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
//    }
//
////    @Override
////    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
////        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated();
//
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.securityContext((securityContext) -> (securityContext).requireExplicitSave(false));
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
