package sn.modelsis.cdmp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import sn.modelsis.cdmp.dbPersist.PersitUsers;
import sn.modelsis.cdmp.repositories.*;

/**
 * @author SNDIAGNEF
 *
 */

@SpringBootApplication
public class CdmpApplication implements InitializingBean, CommandLineRunner {
  
  private static final Logger log = LoggerFactory.getLogger(CdmpApplication.class);
  
  private final Environment env;


  private final UtilisateurRepository utilisateurRepository;

  private final RoleRepository roleRepository;

  private final PmeRepository pmeRepository;
  
  private final MinistereDepensierRepository mdRepository;

  private  final FormeJuridiqueRepository formeJuridiqueRepository;

    private final CentreDesServicesFiscauxRepository centreFiscalRepository;

    @Value("${server.link_front}")
    private String uilocation; 

    public CdmpApplication(Environment env,
                           UtilisateurRepository utilisateurRepository, RoleRepository roleRepository,
                           PmeRepository pmeRepository, MinistereDepensierRepository mdRepository, FormeJuridiqueRepository formeJuridiqueRepository, CentreDesServicesFiscauxRepository centreFiscalRepository) {
        this.env = env;
      this.utilisateurRepository = utilisateurRepository;
      this.roleRepository = roleRepository;
      this.pmeRepository = pmeRepository;
      this.mdRepository = mdRepository;
        this.formeJuridiqueRepository = formeJuridiqueRepository;
        this.centreFiscalRepository = centreFiscalRepository;
    }

  @Override
  public void afterPropertiesSet() throws Exception {
    Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
    if (activeProfiles.contains("DEV")) {
        log.error("You have misconfigured your application! It should not run " +
            "with both the 'dev' and 'prod' profiles at the same time.");
    }
    
  }
  
  /**
   * Main method, used to run the application.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
      SpringApplication app = new SpringApplication(CdmpApplication.class);
     // DefaultProfileUtil.addDefaultProfile(app);
      Environment env = app.run(args).getEnvironment();
      logApplicationStartup(env);
  }

  private static void logApplicationStartup(Environment env) {
      String protocol = "http";
      if (env.getProperty("server.ssl.key-store") != null) {
          protocol = "https";
      }
      String serverPort = env.getProperty("server.port");
      String contextPath = env.getProperty("server.servlet.context-path");
      if (StringUtils.isBlank(contextPath)) {
          contextPath = "/";
      }
      String hostAddress = "localhost";
      try {
          hostAddress = InetAddress.getLocalHost().getHostAddress();
      } catch (UnknownHostException e) {
          log.warn("The host name could not be determined, using `localhost` as fallback");
      }
      log.info("\n----------------------------------------------------------\n\t" +
              "Application '{}' is running! Access URLs:\n\t" +
              "Local: \t\t{}://localhost:{}{}\n\t" +
              "External: \t{}://{}:{}{}\n\t" +
              "Profile(s): \t{}\n----------------------------------------------------------",
          env.getProperty("spring.application.name"),
          protocol,
          serverPort,
          contextPath,
          protocol,
          hostAddress,
          serverPort,
          contextPath,
          env.getActiveProfiles());

      String configServerStatus = env.getProperty("configserver.status");
      if (configServerStatus == null) {
          configServerStatus = "Not found or not setup for this application";
      }
      log.info("\n----------------------------------------------------------\n\t" +
              "Config Server: \t{}\n----------------------------------------------------------", configServerStatus);
  }

//  @Bean
//  public WebMvcConfigurer corsConfigurer() {
//      return new WebMvcConfigurer() {
//          @Override
//          public void addCorsMappings(CorsRegistry registry) {
//              registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
//          }
//      };
//  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", uilocation));
    configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin",
        "Content-Type", "Accept", "Authorization", "Origin,Accept", "X-Requested-With",
        "Access-Control-Request-Method", "Access-Control-Request-Headers", "enctype"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowCredentials(true);
    configuration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
        "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
  
  @Component
  public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
      String context = request.getRequestURI();
      if (request.getRequestURI().contains("api/") || request.getRequestURI().contains("token")) {
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
            "authorization, content-type, xsrf-token,enctype");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        response.setHeader("Access-Control-Allow-Origin", uilocation);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request, response);
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }

    @Override
    public void run(String... args) throws Exception {

       PersitUsers persitUsers=new PersitUsers(roleRepository,utilisateurRepository,pmeRepository, mdRepository, formeJuridiqueRepository, centreFiscalRepository);
        log.info("Initialisation des differents profils terminée");


  }


}
