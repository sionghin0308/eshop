package com.application.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/page")
public class WebPageController {
	
//	@Autowired
//	private TrafficInformationService trafficInformationService;
	
	@GetMapping(value = "/main")
	public String getMainPage() {
		return "index";
	}
	
//	@GetMapping(value="/traffic/information/list")
//	@ApiOperation(value="getTrafficInformationList", notes="Get Traffic Information Listing")
//	public @ResponseBody List<TrafficInformation> getTrafficInformationList() {
//		
//		List<TrafficInformation> result = trafficInformationService.getTrafficInformationList();
//		if(!CollectionUtils.isEmpty(result)){
//			return result;
//		} else {
//			List<TrafficInformation> empty = new ArrayList<>();
//			return empty;
//		}
//	}
	
}
