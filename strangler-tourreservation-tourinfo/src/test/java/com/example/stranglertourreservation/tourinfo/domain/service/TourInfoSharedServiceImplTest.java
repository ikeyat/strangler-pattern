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
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.example.stranglertourreservation.tourinfo.domain.model.TourInfo;
import com.example.stranglertourreservation.tourinfo.domain.repository.TourInfoRepository;

public class TourInfoSharedServiceImplTest {
	TourInfoSharedServiceImpl tourInfoSharedService;

	TourInfoRepository tourInfoRepository;

	LocalDateTime now;

	@BeforeEach
	public void setUp() {
		tourInfoSharedService = new TourInfoSharedServiceImpl();
		tourInfoRepository = mock(TourInfoRepository.class);
		tourInfoSharedService.tourInfoRepository = tourInfoRepository;
		now = LocalDateTime.now();
	}

	/**
	 * searchTourInfo return no object pattern
	 */
	@Test
	public void testFindOne01() {
		TourInfo info = new TourInfo();

		when(tourInfoRepository.findOneWithDetails("foo")).thenReturn(info);

		// run
		try (MockedStatic<LocalDateTime> mock = mockNow(now)) {
			TourInfo result = tourInfoSharedService.findOneWithDetails("foo");

			// assert
			assertThat(result, is(info));
		}
	}

	@Test
	public void testIsOverPaymentLimitTour01() {
		// normal case
		TourInfo tour = new TourInfo();
		LocalDateTime depDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(7); // within limit
		tour.setDepDay(Date.from(depDay.atZone(ZoneId.systemDefault()).toInstant()));

		try (MockedStatic<LocalDateTime> mock = mockNow(now)) {
			boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
			assertThat(result, is(false));
		}
	}

	@Test
	public void testIsOverPaymentLimitTour02() {
		// invalid case
		TourInfo tour = new TourInfo();
		LocalDateTime depDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(6); // over limit
		tour.setDepDay(Date.from(depDay.atZone(ZoneId.systemDefault()).toInstant()));

		try (MockedStatic<LocalDateTime> mock = mockNow(now)) {
			boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
			assertThat(result, is(true));
		}
	}

	@Test
	public void testIsOverPaymentLimitTour03() {
		// normal case
		TourInfo tour = new TourInfo();
		LocalDateTime depDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(7); // within limit
		tour.setDepDay(Date.from(depDay.atZone(ZoneId.systemDefault()).toInstant()));

		// check whether hh:mm:ss.SSS is ignored
		try (MockedStatic<LocalDateTime> mock = mockNow(
				now.withHour(0).withMinute(0).withSecond(0).withNano(0).plusNanos(1))) {
			boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
			assertThat(result, is(false));
		}
	}

	@Test
	public void testIsOverPaymentLimitTour04() {
		// normal case
		TourInfo tour = new TourInfo();
		LocalDateTime depDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(7); // within limit
		tour.setDepDay(Date.from(depDay.atZone(ZoneId.systemDefault()).toInstant()));

		try (MockedStatic<LocalDateTime> mock = mockNow(now.withHour(0).withMinute(0).withSecond(0).withNano(0)
				.plusHours(23).plusMinutes(59).plusSeconds(59).plusNanos(999999999))) {

			boolean result = tourInfoSharedService.isOverPaymentLimit(tour);
			assertThat(result, is(false));
		}
	}
}
