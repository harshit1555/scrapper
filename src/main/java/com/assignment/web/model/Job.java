package com.assignment.web.model;

public class Job {

	    private String jobTitle;
	    private String description;
	    private String location;
		public String getJobTitle() {
			return jobTitle;
		}
		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		
		public Job(String jobTitle, String description, String location) {
			super();
			this.jobTitle = jobTitle;
			this.description = description;
			this.location = location;
		}
	    	
		public Job(){
			
		}
	    

}
