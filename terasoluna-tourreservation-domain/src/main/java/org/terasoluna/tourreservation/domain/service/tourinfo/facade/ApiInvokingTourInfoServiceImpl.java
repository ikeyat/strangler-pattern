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
package org.terasoluna.tourreservation.domain.service.tourinfo.facade;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.terasoluna.tourreservation.domain.common.facade.PageRangedInputResource;
import org.terasoluna.tourreservation.domain.common.facade.PageRangedOutputResource;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoService;

@Service
public class ApiInvokingTourInfoServiceImpl implements TourInfoService {
	@Inject
	RestTemplate restTemplate;

	@Value("${facade.baseurl}")
	String baseUrl;

	@Override
	public Page<TourInfo> searchTour(TourInfoSearchCriteria criteria, Pageable pageable) {

		PageRangedInputResource<TourInfoSearchCriteria> input = new PageRangedInputResource<>(criteria, pageable);

		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/tourinfoservice").path("/searchTour").build()
				.toUri();

		// TODO Is POST request bad?
		RequestEntity<PageRangedInputResource<TourInfoSearchCriteria>> request = RequestEntity.post(targetUri)
				.body(input);

		// TODO error handling
		ResponseEntity<PageRangedOutputResource<List<TourInfo>>> response = restTemplate.exchange(request,
				new ParameterizedTypeReference<PageRangedOutputResource<List<TourInfo>>>() {
				});

		PageRangedOutputResource<List<TourInfo>> content = response.getBody();

		Page<TourInfo> page = new PageImpl<TourInfo>(content.getResource(), pageable, content.getTotal()); // TODO total
		return page;
	}
}
