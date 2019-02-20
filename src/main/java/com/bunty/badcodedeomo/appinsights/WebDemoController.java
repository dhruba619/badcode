package com.bunty.badcodedeomo.appinsights;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microsoft.applicationinsights.TelemetryClient;

@Controller
@RequestMapping(path="/web")
public class WebDemoController {
	
	@RequestMapping(path="")
	public String gotoHome() {
		return "index";
		
	}
	
	@RequestMapping("/exception")
	public String customevent() {
		TelemetryClient telemetry = new TelemetryClient();

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("User", "Guest User");
		properties.put("Time", "Some time");

		telemetry.trackEvent("EventTracker", properties, null);    	


		try { 
		    throw new Exception("This is only a test!"); 
		} catch (Exception exc) { 
		    telemetry.trackException(exc); 
		    System.out.println("[6] Exception             -- message=\"This is only a test!\""); 
		} 
		return "error";
	}

}
