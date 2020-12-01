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
package com.example.stranglertourreservation.tourinfo.domain.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedInputResource;
import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedOutputResource;
import com.example.stranglertourreservation.tourinfo.domain.model.TourInfo;
import com.example.stranglertourreservation.tourinfo.domain.service.TourInfoService;

@SpringBootTest
@Transactional
@Rollback
public class TourInfoRepositoryImplTest {

	@Autowired
	TourInfoService tourInfoService;

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	String tourCode;

	LocalDateTime depDay;

	LocalDateTime plannedDay;

	String tourAbs;

	String tourName;

	int basePrice;

	TourInfoSearchCriteria criteria;

	@BeforeEach
	public void setUp() {
		criteria = new TourInfoSearchCriteria();
		tourCode = "8888888888";
		depDay = LocalDateTime.of(2014, 2, 2, 0, 0, 0);
		plannedDay = LocalDateTime.of(2013, 12, 31, 0, 0, 0);
		tourAbs = "wonderful travel !";
		tourName = "test tour";
		basePrice = 20000;

		String q = "INSERT INTO tourinfo(tour_code, planned_day, plan_no, tour_name, "
				+ "tour_days, dep_day, ava_rec_max, dep_code, arr_code, accom_code, "
				+ "base_price, conductor, tour_abs) values(:tourCode, :plannedDay, "
				+ ":planNo, :tourName, :tourDays, :depDay, :avaRecMax, :depCode, :arrCode, "
				+ ":accomCode, :basePrice, :conductor, :tourAbs)";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tourCode", tourCode);
		param.put("plannedDay", Date.from(plannedDay.atZone(ZoneId.systemDefault()).toInstant()));
		param.put("planNo", "0101");
		param.put("tourName", tourName);
		param.put("tourDays", 1);
		param.put("depDay", Date.from(depDay.atZone(ZoneId.systemDefault()).toInstant()));
		param.put("avaRecMax", 2147483647);
		param.put("depCode", "01");
		param.put("arrCode", "01");
		param.put("accomCode", "0001");
		param.put("basePrice", basePrice);
		param.put("conductor", 1);
		param.put("tourAbs", tourAbs);
		jdbcTemplate.update(q, param);
	}

	/**
	 * BasePrice and TourDays dont set value Case
	 */
	public void testSearchTourInfo01() {
		// search data
		criteria.setDepDate(Date.from(depDay.atZone(ZoneId.systemDefault()).toInstant()));
		criteria.setAdultCount(1);
		criteria.setArrCode("01");
		criteria.setBasePrice(0);
		criteria.setChildCount(1);
		criteria.setDepCode("01");
		criteria.setTourDays(0);

		PageRangedInputResource<TourInfoSearchCriteria> input = new PageRangedInputResource<>(criteria, 0, 10);

		// run
		PageRangedOutputResource<List<TourInfo>> output = tourInfoService.searchTour(input);

		assertThat(output.getTotal(), is(1L));
		assertThat(input.getOffset(), is(0L));

		TourInfo tour = output.getResource().get(0);

		// assert
		assertThat(tour.getAvaRecMax(), is(2147483647));
		assertThat(tour.getBasePrice(), is(basePrice));
		assertThat(tour.getConductor(), is("1"));
		assertThat(tour.getDepDay().getTime(), is(depDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
		assertThat(tour.getPlannedDay().getTime(),
				is(plannedDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
		assertThat(tour.getTourAbs(), is(tourAbs));
		assertThat(tour.getTourCode(), is(tourCode));
		assertThat(tour.getTourDays(), is(1));
		assertThat(tour.getTourName(), is(tourName));

		assertThat(tour.getAccommodation().getAccomCode(), is("0001"));
		assertThat(tour.getAccommodation().getAccomName(), is("TERASOLUNAホテル第一荘"));
		assertThat(tour.getAccommodation().getAccomTel(), is("018-123-4567"));

		assertThat(tour.getDeparture().getDepCode(), is("01"));
		assertThat(tour.getDeparture().getDepName(), is("北海道"));

	}

	/**
	 * BasePrice and TourDays set value Case
	 */
	@Test
	public void testSearchTourInfo02() {
		// search data
		criteria.setDepDate(Date.from(LocalDate.of(2012, 7, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		criteria.setAdultCount(1);
		criteria.setArrCode("01");
		criteria.setBasePrice(10);
		criteria.setChildCount(1);
		criteria.setDepCode("01");
		criteria.setTourDays(2);

		PageRangedInputResource<TourInfoSearchCriteria> input = new PageRangedInputResource<>(criteria, 0, 10);
		// run
		PageRangedOutputResource<List<TourInfo>> output = tourInfoService.searchTour(input);

		// assert
		assertThat(output.getTotal(), is(0L));
		assertThat(input.getOffset(), is(0L));
	}
}
