package spaApp.update.service;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spaApp.rates.model.Query.QueryWrapper;
import spaApp.rates.model.Rate.LocalRate;
import spaApp.rates.repository.RateRepository;
import spaApp.rates.repository.model.RateDb;
import spaApp.rates.service.RateService;

import java.text.SimpleDateFormat;

@Service
public class UpdateService {

    private static String quote2 = "https://query.yahooapis.com/v1/public/yql?q=select * from yahoo.finance.historicaldata where symbol =\"EUR=X\" and startDate= \"2010-08-01\" and endDate= \"2010-08-04\"&format=json&env=store://datatables.org/alltableswithkeys";

    int daysInterval = 200;
    int NonUpdatePeriod = 3;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateService rateService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void currencyUpdate(String symbol, DateTime endDate) {
        DateTime startDate = getLastEntry(symbol).getDate();
        if (checkToUpdate(startDate, endDate)) {
            getUpdate(symbol, startDate, endDate);
        }
    }

    public void getUpdate(String symbol, DateTime startDate, DateTime endDate) {
        if (!checkAppropriateInteral(startDate, endDate)) {
            while (!checkAppropriateInteral(startDate, endDate)) {
                endDate = startDate.plusDays(daysInterval);
                updateCurrency(symbol, startDate, endDate);
                startDate = getLastEntry(symbol).getDate();
            }
        }
        updateCurrency(symbol, startDate, endDate);
    }

    @ResponseBody
    public void updateCurrency(String symbol, DateTime startDate, DateTime endDate) {
        String query = getQuery(symbol, startDate, endDate);
        QueryWrapper response = restTemplate.getForObject(query, QueryWrapper.class);

        rateService.appendQuerryWrapperToDB(response);

    }

    boolean checkToUpdate(DateTime startDate, DateTime endDate) {
        return (getDaysInterval(startDate, endDate) >= NonUpdatePeriod);

    }

    boolean checkAppropriateInteral(DateTime startDate, DateTime endDate) {
        return (getDaysInterval(startDate, endDate) <= daysInterval);
    }


    int getDaysInterval(DateTime startDate, DateTime endDate) {
        Days days = Days.daysBetween(startDate, endDate);
        return days.getDays();
    }

    LocalRate getLastEntry(String symbol) {
        RateDb lastEntryDB = rateRepository.selectLastEntry(symbol);
        LocalRate lastEntry = rateService.mapToLocalQuote(lastEntryDB);
        return lastEntry;
    }

    private String getQuery(String symbol, DateTime startDate, DateTime endDate) {


        String query = "https://query.yahooapis.com/v1/public/yql?q=select * from yahoo.finance.historicaldata where symbol =\"" +
                symbol +
                "\" and startDate= \"" +
                startDate +
                "\" and endDate= \"" +
                endDate +
                "\"&format=json&env=store://datatables.org/alltableswithkeys";
        return query;
    }

}
