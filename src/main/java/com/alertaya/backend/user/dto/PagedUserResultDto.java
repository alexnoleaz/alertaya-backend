package com.alertaya.backend.user.dto;

import java.util.List;

public class PagedUserResultDto {
	private List<UserDto> items;
	private Long totalCount;

	public PagedUserResultDto(List<UserDto> items, Long totalCount) {
		this.items = items;
		this.totalCount = totalCount;
	}

	public List<UserDto> getItems() {
		return items;
	}

	public Long getTotalCount() {
		return totalCount;
	}
}
