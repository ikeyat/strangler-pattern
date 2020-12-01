package com.example.stranglertourreservation.tourinfo.api.resource;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRangedInputResource<T> {
	long pageSize;
	long offset;
	T resource;
}
