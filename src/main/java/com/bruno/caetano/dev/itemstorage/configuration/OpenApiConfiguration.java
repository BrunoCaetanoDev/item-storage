package com.bruno.caetano.dev.itemstorage.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class OpenApiConfiguration {

	@Bean
	public OpenAPI customOpenAPI(@Value("${application-name}") String appName,
			@Value("${application-description}") String appDescription,
			@Value("${application-version}") String appVersion) {
		SpringDocUtils.getConfig().replaceWithClass(org.springframework.data.domain.Pageable.class,
				org.springdoc.core.converters.models.Pageable.class);
		return new OpenAPI()
				.info(new Info()
						.title(appName.toUpperCase())
						.version(appVersion)
						.description(appDescription)
						.contact(new Contact()
								.name("Bruno Caetano")
								.url("http://bruno-caetano-devfolio.herokuapp.com/")
								.email("brunoaccdev@gmail.com"))
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
	}

}
