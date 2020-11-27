package org.terasoluna.tourreservation.domain.common.facade;

import org.springframework.data.domain.Pageable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRangedInputResource<T> {
	long pageSize;
	long offset;
	T resource;

	public PageRangedInputResource(T resource, Pageable page) {
		this.pageSize = page.getPageSize();
		this.offset = page.getOffset();
		this.resource = resource;
	}
}
