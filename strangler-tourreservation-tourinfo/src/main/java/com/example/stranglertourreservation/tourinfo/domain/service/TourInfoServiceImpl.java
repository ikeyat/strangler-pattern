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

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedInputResource;
import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedOutputResource;
import com.example.stranglertourreservation.tourinfo.domain.model.TourInfo;
import com.example.stranglertourreservation.tourinfo.domain.repository.TourInfoRepository;
import com.example.stranglertourreservation.tourinfo.domain.repository.TourInfoSearchCriteria;

@Service
@Transactional
public class TourInfoServiceImpl implements TourInfoService {

	@Autowired
	TourInfoRepository tourInfoRepository;

	@Override
	public PageRangedOutputResource<List<TourInfo>> searchTour(PageRangedInputResource<TourInfoSearchCriteria> input) {
		TourInfoSearchCriteria criteria = input.getResource();

		long total = tourInfoRepository.countBySearchCriteria(criteria);
		List<TourInfo> content;
		if (0 < total) {
			content = tourInfoRepository.findPageBySearchCriteria(criteria, input);
		} else {
			content = Collections.emptyList();
		}

		return new PageRangedOutputResource<List<TourInfo>>(content, total);
	}
}
