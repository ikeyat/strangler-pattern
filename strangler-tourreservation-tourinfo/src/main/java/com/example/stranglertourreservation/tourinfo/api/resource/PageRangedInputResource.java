package com.example.stranglertourreservation.tourinfo.api.resource;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRangedInputResource<T> {
	long pageSize;
	long offset;
	T resource;

	public PageRangedInputResource(T resource, long offset, long pageSize) {
		this.offset = offset;
		this.pageSize = pageSize;
		this.resource = resource;
	}
}
