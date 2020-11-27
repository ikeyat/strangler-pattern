package org.terasoluna.tourreservation.domain.common.facade;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRangedOutputResource<T> {
	long total;
	T resource;

	public PageRangedOutputResource(T resource, long total) {
		this.total = total;
		this.resource = resource;
	}
}
