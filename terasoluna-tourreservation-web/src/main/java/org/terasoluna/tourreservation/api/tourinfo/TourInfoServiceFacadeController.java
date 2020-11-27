package org.terasoluna.tourreservation.api.tourinfo;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terasoluna.tourreservation.domain.common.facade.PageRangedInputResource;
import org.terasoluna.tourreservation.domain.common.facade.PageRangedOutputResource;
import org.terasoluna.tourreservation.domain.model.TourInfo;
import org.terasoluna.tourreservation.domain.repository.tourinfo.TourInfoSearchCriteria;
import org.terasoluna.tourreservation.domain.service.tourinfo.legacy.TourInfoServiceLegacy;

@RestController
@RequestMapping("/api/facade/tourinfoservice")
public class TourInfoServiceFacadeController {
	@Inject
	@Qualifier("tourInfoServiceImpl")
	private TourInfoServiceLegacy tourInfoService;

	@PostMapping("/searchTour")
	public PageRangedOutputResource<List<TourInfo>> searchTour(
			@RequestBody PageRangedInputResource<TourInfoSearchCriteria> input) {
		// TODO error handling
		return tourInfoService.searchTour(input);
	}
}
