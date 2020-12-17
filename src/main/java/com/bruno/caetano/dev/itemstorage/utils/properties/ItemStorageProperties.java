package com.bruno.caetano.dev.itemstorage.utils.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ItemStorageProperties {

	@Value("${server.servlet.context-path:#{null}}")
	private String servletContextPath;

	@Value("${spring.application.name:item-storage}")
	public String applicationName;
}
