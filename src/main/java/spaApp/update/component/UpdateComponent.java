package spaApp.update.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spaApp.update.service.UpdateService;


@Component
public class UpdateComponent {

    @Autowired
    UpdateService updateService = new UpdateService();

    private static final Logger log = (Logger) LoggerFactory.getLogger(UpdateComponent.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        updateService.checkUpdate("GBP%3dX",new DateTime());
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

}
