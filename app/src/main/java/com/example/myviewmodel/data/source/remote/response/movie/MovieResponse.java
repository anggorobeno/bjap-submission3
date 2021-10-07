package com.example.myviewmodel.data.source.remote.response.movie;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieResponse{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<MovieResult> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<MovieResult> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}