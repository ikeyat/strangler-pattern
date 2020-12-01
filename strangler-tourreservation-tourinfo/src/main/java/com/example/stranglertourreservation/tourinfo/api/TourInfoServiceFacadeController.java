package com.example.stranglertourreservation.tourinfo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedInputResource;
import com.example.stranglertourreservation.tourinfo.api.resource.PageRangedOutputResource;
import com.example.stranglertourreservation.tourinfo.domain.model.TourInfo;
import com.example.stranglertourreservation.tourinfo.domain.repository.TourInfoSearchCriteria;
import com.example.stranglertourreservation.tourinfo.domain.service.PriceCalculateOutput;
import com.example.stranglertourreservation.tourinfo.domain.service.PriceCalculateSharedService;
import com.example.stranglertourreservation.tourinfo.domain.service.TourInfoService;
import com.example.stranglertourreservation.tourinfo.domain.service.TourInfoSharedService;

@RestController
@RequestMapping("/api/modern/tourinfoservice")
public class TourInfoServiceFacadeController {
	@Autowired
	private TourInfoService tourInfoService;

	@Autowired
	private PriceCalculateSharedService priceCalculateSharedService;

	@Autowired
	private TourInfoSharedService tourInfoSharedService;

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
