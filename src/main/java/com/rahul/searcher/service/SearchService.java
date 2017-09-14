package com.rahul.searcher.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.searcher.config.PropertyFileReader;
import com.rahul.searcher.service.dto.SearchDTO;
import com.rahul.searcher.service.dto.SearchResultDTO;

@Service
public class SearchService {

	private final Logger log = LoggerFactory.getLogger(SearchService.class);

	@Autowired
	private PropertyFileReader propertyFileReader;

	private File file;

	private List<File> allFiles;

	private Set<File> cFiles;
	private Set<String> fileTypes;
	private Set<SearchResultDTO> searchResults;
	private List<SearchDTO> cDetials;

	@PostConstruct
	public void init() {
		this.file = new File(propertyFileReader.getProjectDir());
		allFiles = new ArrayList<File>();
		log.info("SET the project directory to : {}", propertyFileReader.getProjectDir());
		fileTypes = new HashSet<>();
		searchResults = new HashSet<>();
		cDetials = new ArrayList<>();
		cFiles = new HashSet<>();
		try {
			intializeSearch();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void intializeSearch() throws Exception {
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
						log.info("{}", currentFile.getName());
						if (ext.length > 1) {
							fileTypes.add(ext[1]);
							if ("c".equalsIgnoreCase(ext[1])) {
								cFiles.add(currentFile);
							}
						}
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
				Scanner scanner = null;
				try {
					scanner = new Scanner(file);
				} catch (Exception e) {
					throw new Exception();
				}
				while (scanner.hasNextLine()) {
					sb.append(scanner.nextLine()).append("\n");
				}
				searchResultDTO.setCodeSnippet(sb.toString());
				searchResultDTO.setPath(file.getAbsolutePath());
				searchResults.add(searchResultDTO);
			}
			ExecutorService executors = Executors.newFixedThreadPool(10);
			List<SearchDTO> result = new ArrayList<>();
			for (File ccode : cFiles) {
				StringBuffer stringBuffer = new StringBuffer();
				Scanner sc = new Scanner(ccode);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					if (line.contains("#")) {
						continue;
					} else if (line.contains("/*")) {
						stringBuffer.append(line.substring(0, line.indexOf('/')));
						while (!line.contains("*/")) {
							line = sc.nextLine();
						}
						stringBuffer.append(line.substring(line.indexOf("/")));
					} else if(line.contains("//")){
						stringBuffer.append(line.substring(0,line.indexOf('/')));
					}else
						stringBuffer.append(line);

				}
				CReader cReader = new CReader();
				cReader.setcFile(stringBuffer.toString());
				cReader.setcName(ccode.getName());
				cReader.setPath(ccode.getAbsolutePath());
				Future<SearchDTO> cFileDetails = executors.submit(cReader);
				result.add(cFileDetails.get());
			}
			cDetials.addAll(result);

		} else {
			log.error("The project directory is not set.");
		}
		for (String f : fileTypes) {
			log.info("There are the following file types {}", f);
		}
	}

	public Collection<SearchResultDTO> searchFiles(String searchQuery) {
		Set<SearchResultDTO> result = new LinkedHashSet<>();
		for (SearchResultDTO searchResultDTO : searchResults) {
			if (searchResultDTO.getCodeSnippet().contains(searchQuery)
					|| searchResultDTO.getFileName().contains(searchQuery)
					|| searchResultDTO.getPath().contains(searchQuery)) {
				result.add(searchResultDTO);
				log.info("Found the search query in generating result {}", searchResultDTO);
			}

		}

		return result;

	}

	public Collection<SearchDTO> searchMethodsInCCode(String searchQuery) {
		Set<SearchDTO> result = new LinkedHashSet<>();
		log.info("The size of result is {}", cDetials.size());
		for (SearchDTO searchDTO : cDetials) {
			log.info("The current dto is {}", searchDTO);
			for (String str : searchDTO.getFunctionNames())
				if (str.contains(searchQuery)) {
					result.add(searchDTO);
					log.info("Found the search query in generating result {}", searchDTO);
				}

		}

		return result;
	}

}
