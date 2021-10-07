package com.example.myviewmodel.data.source.remote.response.tv;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TvResponse{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<TvResult> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<TvResult> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}