package org.terasoluna.tourreservation.api.tourinfo;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.terasoluna.tourreservation.domain.common.facade.PageRangedInputResource;
import org.terasoluna.tourreservation.domain.common.facade.PageRangedOutputResource;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.domain.service.tourinfo.PriceCalculateOutput;
import org.terasoluna.tourreservation.domain.service.tourinfo.legacy.PriceCalculateSharedServiceLegacy;
import org.terasoluna.tourreservation.domain.service.tourinfo.legacy.TourInfoServiceLegacy;
import org.terasoluna.tourreservation.domain.service.tourinfo.legacy.TourInfoSharedServiceLegacy;

@RestController
@RequestMapping("/api/facade/tourinfoservice")
public class TourInfoServiceFacadeController {
	@Inject
	private TourInfoServiceLegacy tourInfoService;

	@Inject
	private PriceCalculateSharedServiceLegacy priceCalculateSharedService;

	@Inject
	private TourInfoSharedServiceLegacy tourInfoSharedService;

	@PostMapping("/searchTour")
	public PageRangedOutputResource<List<TourInfo>> searchTour(
			@RequestBody PageRangedInputResource<TourInfoSearchCriteria> input) {
		// TODO error handling
		return tourInfoService.searchTour(input);
	}

	@GetMapping("/calculatePrice")
	public PriceCalculateOutput calculatePrice(@RequestParam("basePrice") Integer basePrice,
			@RequestParam("adultCount") Integer adultCount, @RequestParam("childCount") Integer childCount) {
		// TODO error handling
		return priceCalculateSharedService.calculatePrice(basePrice, adultCount, childCount);
	}

	@GetMapping("/findOneWithDetails")
	public TourInfo findOneWithDetails(@RequestParam("tourCode") String tourCode) {
		// TODO error handling
		return tourInfoSharedService.findOneWithDetails(tourCode);
	}

	@PostMapping("/isOverPaymentLimit")
	public boolean isOverPaymentLimit(@RequestBody TourInfo tour) {
		// TODO error handling
		return tourInfoSharedService.isOverPaymentLimit(tour);
	}

	@GetMapping("/findOneWithDetailsForUpdate")
	public TourInfo findOneWithDetailsForUpdate(@RequestParam("tourCode") String tourCode) {
		// TODO error handling
		return tourInfoSharedService.findOneWithDetailsForUpdate(tourCode);
	}
}
