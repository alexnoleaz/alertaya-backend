package com.alertaya.backend.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class PagedUserResultRequestDto {
	private static final int DEFAULT_MAX_RESULT_COUNT = 10;

	@Min(1)
	@Max(Integer.MAX_VALUE)
	private int maxResultCount = DEFAULT_MAX_RESULT_COUNT;

	@Min(0)
	@Max(Integer.MAX_VALUE)
	private int skipCount;

	private String keyword;

	public int getMaxResultCount() {
		return maxResultCount;
	}

	public void setMaxResultCount(int maxResultCount) {
		this.maxResultCount = maxResultCount;
	}

	public int getSkipCount() {
		return skipCount;
	}

	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
