package com.sa.movieinfoservice.model;

public class Movie {

	public String movideId;
	public String name;
	
	public Movie(String movideId, String name) {
		super();
		this.movideId = movideId;
		this.name = name;
	}

	public String getMovideId() {
		return movideId;
	}

	public void setMovideId(String movideId) {
		this.movideId = movideId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
