package spaApp.update.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import spaApp.update.service.UpdateService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UpdateComponent {

    @Autowired
    UpdateService updateService = new UpdateService();

    private static final Logger log = (Logger) LoggerFactory.getLogger(UpdateComponent.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void updateRates() {
        updateService.updateAllInstruments();
        log.info("Currency rates updated{}", dateFormat.format(new Date()));
    }
}
