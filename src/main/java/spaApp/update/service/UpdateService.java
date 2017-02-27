package spaApp.update.service;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import spaApp.financial_instruments.model.FinInstrument;
import spaApp.financial_instruments.service.FinInstrumentService;
import spaApp.rates.model.Query.QueryWrapper;
import spaApp.rates.service.RateService;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UpdateService {


    private final int daysInterval = 200;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RateService rateService;

    @Autowired
    private FinInstrumentService finInstrumentService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void updateAllInstruments(){
        List<FinInstrument> finInstrumentList= finInstrumentService.getAllSymbolCodes();
        for (FinInstrument finInstrument:finInstrumentList) {
            currencyUpdate(finInstrument);
        }
    }

    private  void currencyUpdate(FinInstrument finInstrument) {
            DateTime startDate = rateService.getLastEntry(finInstrument.getYahooCode()).getDate().plusDays(1);
            getUpdate(finInstrument.getYahooCode(), startDate, new DateTime());
    }

    private  void getUpdate(String symbol, DateTime startDate, DateTime endDate) {
        if (!checkAppropriateInteral(startDate, endDate)) {
            while (!checkAppropriateInteral(startDate, endDate)) {
                endDate = startDate.plusDays(daysInterval);
                updateCurrency(symbol, startDate, endDate);
                startDate = rateService.getLastEntry(symbol).getDate().plusDays(1);
                endDate = startDate.plusDays(daysInterval);
            }
        }
        updateCurrency(symbol, startDate, endDate);
    }

    @ResponseBody
    private void updateCurrency(String symbol, DateTime startDate, DateTime endDate) {
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

    private boolean checkAppropriateInteral(DateTime startDate, DateTime endDate) {
        return (getDaysInterval(startDate, endDate) <= daysInterval);
    }


    private int getDaysInterval(DateTime startDate, DateTime endDate) {
        Days days = Days.daysBetween(startDate, endDate);
        return days.getDays();
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
