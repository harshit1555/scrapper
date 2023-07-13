package com.assignment.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.web.model.Job;
import com.assignment.web.service.JobService;

@RestController
public class JobController {

		@Autowired
	    private JobService jobService;
	    
	    
	    @GetMapping("/jobs")
	    public ResponseEntity<String> scrapeDataAndSaveToCSV(
	            @RequestParam(value = "title") String jobTitle,
	            @RequestParam(value = "location") String location) {
	        
	        try {
	            List<Job> jobs = jobService.scrapeJobs(jobTitle, location);
	            
	            // Generate a unique filename for each CSV file
	            String filename = "jobs_" + System.currentTimeMillis() + ".csv";
	            
	            jobService.saveDataToCSV(jobs, filename);
	            
	            return ResponseEntity.ok("Details has been scraped and saved to " + filename);
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error occurred while scraping  data: " + e.getMessage());
	        }
	    }
	}

	

