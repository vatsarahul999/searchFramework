package com.rahul.searcher.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.searcher.config.PropertyFileReader;
import com.rahul.searcher.service.dto.SearchResultDTO;

@Service
public class SearchService {

	private final Logger log = LoggerFactory.getLogger(SearchService.class);

	@Autowired
	private PropertyFileReader propertyFileReader;

	private File file;

	private List<File> allFiles;

	private Set<String> fileTypes;

	@PostConstruct
	public void init() {
		this.file = new File(propertyFileReader.getProjectDir());
		allFiles = new ArrayList<File>();
		log.info("SET the project directory to : {}", propertyFileReader.getProjectDir());
		fileTypes = new HashSet<>();

		intializeSearch();

	}

	public void intializeSearch() {
		Queue<File> queue = new LinkedList<File>();
		if (file != null) {
			queue.add(file);
			while (!queue.isEmpty()) {
				File rootFile = queue.poll();
				for (File currentFile : rootFile.listFiles()) {
					if (currentFile == null)
						continue;
					if (currentFile.isFile() && !currentFile.getAbsolutePath().contains(".git")) {
						allFiles.add(currentFile);
						String[] ext = currentFile.getName().split("\\.");
						log.info("{}",currentFile.getName());
						if (ext.length > 1)
							fileTypes.add(ext[1]);
						log.info("Identified  {} as a file", currentFile.getAbsolutePath());
					} else if (currentFile.isDirectory()) {
						queue.add(currentFile);
						log.info("Identified  {} as a directory", currentFile.getAbsolutePath());
					} else {
						log.error("Could not identify the file {}", currentFile.getAbsolutePath());
					}
				}
			}

			for (File file : allFiles) {
				SearchResultDTO searchResultDTO = new SearchResultDTO();
				searchResultDTO.setFileName(file.getName());
				StringBuffer sb = new StringBuffer();
				Path path = FileSystems.getDefault().getPath(file.getAbsolutePath());

				List<String> lines = null;
				try {
					lines = Files.readAllLines(path);
				} catch (IOException e) {
					//e.printStackTrace();
					log.error("There was an error reading file {}", file.getName());
					continue;
				}
				for (String line : lines) {
					sb.append(line).append("\n");
				}
				searchResultDTO.setCodeSnippet(sb.toString());

			}
		} else {
			log.error("The project directory is not set.");
		}
		for (String f : fileTypes) {
			log.info("There are the following file types {}", f);
		}
	}

	public SearchResultDTO search(String searchQuery) {
		SearchResultDTO result = new SearchResultDTO();

		return result;

	}

}
