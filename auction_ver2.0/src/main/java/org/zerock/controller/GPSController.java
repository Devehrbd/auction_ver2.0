package org.zerock.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.domain.GPSVO;

import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/gps/*")
public class GPSController {

	@PostMapping("/data")
	public void data(GPSVO gpsvo, Model model) {
		model.addAttribute("latitude",gpsvo.getLatitude());
		model.addAttribute("longitude",gpsvo.getLongitude());
	}
}