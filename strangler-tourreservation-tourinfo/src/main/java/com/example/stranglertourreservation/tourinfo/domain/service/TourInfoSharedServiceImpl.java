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

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.stranglertourreservation.tourinfo.domain.model.TourInfo;
import com.example.stranglertourreservation.tourinfo.domain.repository.TourInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TourInfoSharedServiceImpl implements TourInfoSharedService {

	@Autowired
	TourInfoRepository tourInfoRepository;

	@Override
	public TourInfo findOneWithDetails(String tourCode) {
		return tourInfoRepository.findOneWithDetails(tourCode);
	}

	@Override
	public boolean isOverPaymentLimit(TourInfo tour) {
		Assert.notNull(tour, "tour must not be null");
		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime paymentLimit = tour.getPaymentLimit();

		log.debug("today={}, paymentLimit={}", today, paymentLimit);
		return today.isAfter(paymentLimit);
	}

	@Override
	public TourInfo findOneWithDetailsForUpdate(String tourCode) {
		return tourInfoRepository.findOneWithDetailsForUpdate(tourCode);
	}

}
