package com.bunty.badcodedeomo;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryLeakController {
    @Value("${app.simulation.memory.load.enabled}")
    boolean isEnabled;

    @Value("${app.simulation.memory.load.duration}")
    long duration;

    private static List<String> staticList = new ArrayList<>();

    Logger LOG = LoggerFactory.getLogger(HighCPUIntensiveController.class);

    @RequestMapping(path = "/api/simulate/memoryleak", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public void simulateMemoryLeak() {
        for (int i = 0; i < 1000000; i++) {
            byte[] array = new byte[250]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            staticList.add(generatedString);

        }
    }

}
