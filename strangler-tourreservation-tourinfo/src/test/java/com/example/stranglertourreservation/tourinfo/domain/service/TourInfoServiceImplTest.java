/*
 * Copyright(c) 2013 NTT DATA Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.example.stranglertourreservation.tourinfo.domain.service;

import static com.example.stranglertourreservation.tourinfo.domain.testutil.MockUtils.mockNow;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedInputResource;
import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedOutputResource;
import com.example.stranglertourreservation.tourinfo.domain.model.Arrival;
import com.example.stranglertourreservation.tourinfo.domain.model.Departure;
import com.example.stranglertourreservation.tourinfo.domain.model.TourInfo;
import com.example.stranglertourreservation.tourinfo.domain.repository.TourInfoRepository;
import com.example.stranglertourreservation.tourinfo.domain.repository.TourInfoSearchCriteria;

public class TourInfoServiceImplTest {
	TourInfoServiceImpl tourInfoService;

	TourInfoRepository tourInfoRepository;

	LocalDateTime now;

	@BeforeEach
	public void setUp() {
		tourInfoService = new TourInfoServiceImpl();
		tourInfoRepository = mock(TourInfoRepository.class);
		tourInfoService.tourInfoRepository = tourInfoRepository;
		now = LocalDateTime.now();
	}

	/**
	 * searchTourInfo return one object pattern
	 */
	@Test
	public void testSearchTourInfo01() {

		TourInfoSearchCriteria criteria = new TourInfoSearchCriteria();

		List<TourInfo> mockedList = new ArrayList<TourInfo>();

		TourInfo info = new TourInfo();
		Arrival a = new Arrival();
		a.setArrCode("1234");
		info.setArrival(a);

		Departure departure = new Departure();
		departure.setDepCode("5678");
		info.setDeparture(departure);
		info.setTourCode("12345678");
		mockedList.add(info);

		PageRangedOutputResource<List<TourInfo>> page = new PageRangedOutputResource<>(mockedList, 1L);

		when(tourInfoRepository.findPageBySearchCriteria(criteria, new PageRangedInputResource<>(criteria, 0, 10)))
				.thenReturn(mockedList);

		when(tourInfoRepository.countBySearchCriteria(criteria)).thenReturn(1L);

		// run
		try (MockedStatic<LocalDateTime> mock = mockNow(now)) {
			PageRangedOutputResource<List<TourInfo>> result = tourInfoService
					.searchTour(new PageRangedInputResource<>(criteria, 0, 10));

			// assert
			assertThat(result, is(page));
		}
	}
}
