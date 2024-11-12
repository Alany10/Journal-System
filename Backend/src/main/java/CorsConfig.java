
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Tillåter alla vägar
                .allowedOrigins("http://localhost:5173")  // Tillåt frontend på localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Tillåt dessa metoder
                .allowedHeaders("*")  // Tillåt alla headers
                .allowCredentials(true)  // Tillåt cookies eller annan autentisering
                .maxAge(3600);  // CORS preflight-cache i sekunder
    }
}
