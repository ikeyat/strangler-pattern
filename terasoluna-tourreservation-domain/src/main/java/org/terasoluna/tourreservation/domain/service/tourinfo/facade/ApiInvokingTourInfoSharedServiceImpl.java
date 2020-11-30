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

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.service.tourinfo.TourInfoSharedService;

@Service
@Transactional
public class ApiInvokingTourInfoSharedServiceImpl implements TourInfoSharedService {
	@Inject
	RestTemplate restTemplate;

	@Value("${facade.baseurl}")
	String baseUrl;

	@Override
	public TourInfo findOneWithDetails(String tourCode) {
		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/tourinfoservice").path("/findOneWithDetails")
				.queryParam("tourCode", tourCode).build().toUri();

		// TODO error handling
		return restTemplate.getForObject(targetUri, TourInfo.class);
	}

	@Override
	public boolean isOverPaymentLimit(TourInfo tour) {
		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/tourinfoservice").path("/isOverPaymentLimit")
				.build().toUri();

		// TODO error handling
		// TODO Is POST bad? 
		return restTemplate.postForObject(targetUri, tour, Boolean.class);
	}

	// TODO transaction
	@Override
	public TourInfo findOneWithDetailsForUpdate(String tourCode) {
		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/tourinfoservice").path("/findOneWithDetailsForUpdate")
				.queryParam("tourCode", tourCode).build().toUri();

		// TODO error handling
		return restTemplate.getForObject(targetUri, TourInfo.class);
	}

}
