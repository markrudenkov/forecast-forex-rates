package spaApp.update.service;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spaApp.rates.model.Query.QueryWrapper;

import spaApp.rates.model.Query.Rate;
import spaApp.rates.repository.RateRepository;
import spaApp.rates.repository.model.RateDb;
import spaApp.rates.service.RateService;

import java.text.SimpleDateFormat;

@Service
public class UpdateService {


    int daysInterval = 200;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateService rateService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void currencyUpdate(String symbol, DateTime endDate) {
        DateTime startDate = getLastEntry(symbol).getDate();
        getUpdate(symbol, startDate, endDate);
    }

    public void getUpdate(String symbol, DateTime startDate, DateTime endDate) {
        if (!checkAppropriateInteral(startDate, endDate)) {
            while (!checkAppropriateInteral(startDate, endDate)) {
                endDate = startDate.plusDays(daysInterval);
                updateCurrency(symbol, startDate, endDate);
                startDate = getLastEntry(symbol).getDate().plusDays(1);
                endDate = startDate.plusDays(daysInterval);
            }
        }
        updateCurrency(symbol, startDate, endDate);
    }

    @ResponseBody
    public void updateCurrency(String symbol, DateTime startDate, DateTime endDate) {
        String query = getQuery(symbol, startDate, endDate);
        try
        {
            QueryWrapper response = restTemplate.getForObject(query, QueryWrapper.class);
            rateService.appendQuerryWrapperToDB(response);
        }
        catch (Exception e)
        {
            //In this case error is caused by the lack of new data from Yahoo
        }

    }

    boolean checkAppropriateInteral(DateTime startDate, DateTime endDate) {
        return (getDaysInterval(startDate, endDate) <= daysInterval);
    }


    int getDaysInterval(DateTime startDate, DateTime endDate) {
        Days days = Days.daysBetween(startDate, endDate);
        return days.getDays();
    }

    @Transactional
    Rate getLastEntry(String symbol) {
        RateDb lastEntryDB = rateRepository.selectLastEntry(symbol);
        Rate lastEntry = rateService.mapToLocalQuote(lastEntryDB);
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
