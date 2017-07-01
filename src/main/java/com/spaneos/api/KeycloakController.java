package com.spaneos.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeycloakController {

	public static final Logger LOGGER = LoggerFactory.getLogger(KeycloakController.class);

	@GetMapping(value = "/kcinit")
	public Object getKeycloakInitJson(HttpServletRequest request) throws IOException {
		String path = request.getServerName();

		if (path.indexOf("localhost") != -1) {
			File file = ResourceUtils.getFile("classpath:dev-keycloak.json");
			LOGGER.info("requested from local machine and origin is : {} and returning file path is {}", path,
					file.toPath());
			String content = new String(Files.readAllBytes(file.toPath()));
			LOGGER.info("File content is : {} ", content);
			return content;
		}

		String[] domains = path.split("\\.");
		LOGGER.info("request origin is : {} and split by . is : {}", path, domains);
		if (domains.length < 3) {
			throw new IllegalStateException("Not able to resolve realm from the request path!");
		}

		String realm = domains[0];

		File file = ResourceUtils.getFile("classpath:" + realm + "-keycloak.json");
		String content = new String(Files.readAllBytes(file.toPath()));
		return content;
	}
}
