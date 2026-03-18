package org.nacosdemo.tlmallfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/order")
	public String order() {
		return "order";
	}
}
