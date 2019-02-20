package com.bunty.badcodedeomo.simulations;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HighDiskWriteControoler {

    @Value("${app.simulation.disk.load.enabled}")
    boolean isEnabled;
    
    @Value("${app.simulation.disk.load.duration}")
    long duration;
    
    @Value("${app.simulation.disk.load.threads}")
    int numThreads;

    Logger LOG = LoggerFactory.getLogger(HighCPUIntensiveController.class);

    @RequestMapping(path = "/api/simulate/diskload", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public void simulateMemoryLoad() {
        if (isEnabled) {
            for(int i=0; i<numThreads; i++) {
                new LoadCreatorOnDisk("Thread-"+i, duration).start();
            }

        }
    }

    private static class LoadCreatorOnDisk extends Thread {
        
        private long duration;

        public LoadCreatorOnDisk(String name, long duration) {
            super(name);
            this.duration = duration;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < duration) {
                try {
                    String randomFile = UUID.randomUUID().toString() +".txt";
                    FileWriter writer = new FileWriter(randomFile, true);
                    writer.write("Hello World");
                    writer.write("\r\n");
                    writer.write("Simulating load");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
