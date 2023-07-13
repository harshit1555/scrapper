package com.assignment.web.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import com.assignment.web.model.Job;

import io.github.bonigarcia.wdm.WebDriverManager;

@Service
public class JobService {

	private static final String DICE_URL = "https://www.dice.com";

	public List<Job> scrapeJobs(String jobTitle, String location) throws IOException {
		List<Job> jobs = new ArrayList<>();
		String searchUrl = buildSearchUrl(jobTitle, location);
		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		try {

			driver.get(searchUrl);

			Thread.sleep(5000);

			List<WebElement> jobElements = driver.findElements(By.cssSelector(".card.search-card"));
			for (WebElement jobElement : jobElements) {
				String title = jobElement.findElement(By.cssSelector(".card-title-link")).getText();
				String description = jobElement.findElement(By.cssSelector(".card-description")).getText();
				String loc = jobElement.findElement(By.cssSelector(".search-result-location")).getText();

				Job job = new Job(title, description, loc);
				jobs.add(job);

			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}

		return jobs;
	}

	private String buildSearchUrl(String jobTitle, String location) {
		return DICE_URL + "/jobs?q=" + jobTitle.replace(" ", "+") + "&l=" + location.replace(" ", "+");
	}

	public void saveDataToCSV(List<Job> jobs, String filename) throws IOException {
		FileWriter csvWriter = new FileWriter(filename);

		// Write CSV header
		csvWriter.append("Job Title,Location,Description\n");

		for (Job job : jobs) {
			csvWriter.append(escapeCommaCharacters(job.getJobTitle())).append(",")
					.append(escapeCommaCharacters(job.getLocation())).append(",")
					.append(escapeCommaCharacters(job.getDescription())).append("\n");
		}

		csvWriter.flush();
		csvWriter.close();
	}

	private String escapeCommaCharacters(String text) {
		// For example, if a comma is present in the text, surround it with / /
		if (text.contains(",")) {
			return "\"" + text + "\"";
		}
		return text;
	}
}
