package com.bunty.badcodedeomo.appinsights;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;


@Configuration
public class AppInsightsConfig {
	
	@Value("${azure.appinsight.instrument.key}")
	private String APPLICATION_INSIGHTS_IKEY;

//Initialize AI TelemetryConfiguration via Spring Beans
    @Bean
    public String telemetryConfig() {
        String telemetryKey = APPLICATION_INSIGHTS_IKEY;
        if (telemetryKey != null) {
            TelemetryConfiguration.getActive().setInstrumentationKey(telemetryKey);
        }
        return telemetryKey;
    }

//Set AI Web Request Tracking Filter
    @Bean
    public FilterRegistrationBean<WebRequestTrackingFilter> aiFilterRegistration(@Value("${spring.application.name:application}") String applicationName) {
       FilterRegistrationBean<WebRequestTrackingFilter> registration = new FilterRegistrationBean<>();
       registration.setFilter(new WebRequestTrackingFilter(applicationName));
       registration.setName("webRequestTrackingFilter");
       registration.addUrlPatterns("/*");
       registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
       return registration;
   } 

      //Set up AI Web Request Tracking Filter
    @Bean(name = "WebRequestTrackingFilter")
    public Filter webRequestTrackingFilter(@Value("${spring.application.name:application}") String applicationName) {
        return new WebRequestTrackingFilter(applicationName);
    }	
}