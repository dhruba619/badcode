package com.bunty.badcodedeomo;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MemoryIntensiveController {
    @Value("${app.simulation.memory.load.enabled}")
    boolean isEnabled;
    
    @Value("${app.simulation.memory.load.duration}")
    long duration;

    Logger LOG = LoggerFactory.getLogger(HighCPUIntensiveController.class);

    @RequestMapping(path = "/api/simulate/memoryload", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public void simulateMemoryLoad() {
        if (isEnabled) {
            LOG.info("Genrating load on memory");
            long startTime = System.currentTimeMillis();
            Vector<Byte[]> v = new Vector<>();
            while (System.currentTimeMillis() - startTime < duration)
            {
              Byte b[] = new Byte[1048576];
              v.add(b);
              Runtime rt = Runtime.getRuntime();
              LOG.info( "free memory: " + rt.freeMemory() );
            }
        }
    }

}
