package net.bddtrader;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:3000");
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setUrl("https://www.serenity-dojo.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("BDD Trader API")
                .version("1.0")
                .contact(contact)
                .description("Serenity Dojo Tutorial")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
