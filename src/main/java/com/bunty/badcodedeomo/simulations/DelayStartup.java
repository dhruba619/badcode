package com.bunty.badcodedeomo.simulations;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DelayStartup {

    private static final Logger LOG 
      = LoggerFactory.getLogger(DelayStartup.class);

    @Value("${app.simulation.startup.delay.ms}")
    private int startupDelay;
 
    @PostConstruct
    public void init() {
      LOG.info("PAUSING STARTUP BY: "+startupDelay +" ms");
      try {
        Thread.sleep(startupDelay);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      LOG.info("RESUMING STARTUP");
    }
}

