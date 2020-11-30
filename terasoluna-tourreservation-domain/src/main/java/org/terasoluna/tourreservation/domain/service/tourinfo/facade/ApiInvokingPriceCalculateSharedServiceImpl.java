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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateSharedService;

@Service
public class ApiInvokingPriceCalculateSharedServiceImpl implements PriceCalculateSharedService {
	@Inject
	RestTemplate restTemplate;

	@Value("${facade.baseurl}")
	String baseUrl;

	@Override
	public PriceCalculateOutput calculatePrice(Integer basePrice, Integer adultCount, Integer childCount) {
		URI targetUri = UriComponentsBuilder.fromUriString(baseUrl).path("/tourinfoservice").path("/calculatePrice")
				.queryParam("basePrice", basePrice).queryParam("adultCount", adultCount)
				.queryParam("childCount", childCount).build().toUri();

		// TODO error handling
		return restTemplate.getForObject(targetUri, PriceCalculateOutput.class);
	}

}
