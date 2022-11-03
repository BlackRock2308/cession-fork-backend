package sn.modelsis.cdmp;

import java.net.InetAddress;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sn.modelsis.cdmp.dbPersist.PersistStatus;
import sn.modelsis.cdmp.repositories.StatutRepository;

/**
 * @author SNDIAGNEF
 *
 */

@SpringBootApplication
public class CdmpApplication implements InitializingBean, CommandLineRunner {
  
  private static final Logger log = LoggerFactory.getLogger(CdmpApplication.class);
  
  private final Environment env;

  private final StatutRepository statutRepository;
  
  public CdmpApplication(Environment env, StatutRepository statutRepository) {
    this.env = env;
      this.statutRepository = statutRepository;
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

  @Bean
  public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
          @Override
          public void addCorsMappings(CorsRegistry registry) {
              registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "OPTIONS", "PUT");
          }
      };
  }
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
//				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
//		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
//				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//		return new CorsFilter(urlBasedCorsConfigurationSource);
//	}


    @Override
    public void run(String... args) throws Exception {
        log.info("Initialisation des differents statuts...");

        PersistStatus persistStatus=new PersistStatus(statutRepository);
        log.info("Initialisation des differents statuts terminée");

    }
}
