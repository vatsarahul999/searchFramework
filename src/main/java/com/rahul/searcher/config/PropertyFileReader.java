package com.rahul.searcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertyFileReader {

	@Value("${projectDir}")
	private String projectDir;

	public String getProjectDir() {
		return projectDir;
	}

}
