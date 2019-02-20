package com.bunty.badcodedeomo.simulations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HighCPUIntensiveController {
    
    
    @Value("${app.simulation.cpu.load.numberofcores}")
    int numCore;
    
    @Value("${app.simulation.cpu.load.threadspercore}")
    int numThreadsPerCore;
    
    @Value("${app.simulation.cpu.load.duration}")
    long duration;
    
    @Value("${app.simulation.cpu.load.enabled}")
    boolean isEnabled;
    
    double load =0.8;
    
    Logger LOG = LoggerFactory.getLogger(HighCPUIntensiveController.class);
    
    @RequestMapping(path="/api/simulate/cpuload", method=RequestMethod.GET)
    @ResponseStatus(code=HttpStatus.OK)
    public void simulateCpuLoad() {
        if(isEnabled) {
            LOG.info("Genrating load on CPU");
            for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
                new LoadCreator("Thread" + thread, load, duration).start();
            }
        }        
    }
    
  
    
    private static class LoadCreator extends Thread {
        private double load;
        private long duration;


        public LoadCreator(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                while (System.currentTimeMillis() - startTime < duration) {
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
