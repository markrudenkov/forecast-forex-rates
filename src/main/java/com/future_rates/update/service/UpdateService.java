package com.future_rates.update.service;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.future_rates.financial_instruments.model.FinInstrument;
import com.future_rates.financial_instruments.service.FinInstrumentService;
import com.future_rates.rates.model.Query.QueryWrapper;
import com.future_rates.rates.service.RateService;

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

    public void updateAllInstruments() {
        List<FinInstrument> finInstrumentList = finInstrumentService.getAllSymbolCodes();
        int i=0;
        for (FinInstrument finInstrument : finInstrumentList) {
            currencyUpdate(finInstrument);
            i++;
        }
    }

    @Transactional
    private void currencyUpdate(FinInstrument finInstrument) {
        DateTime startDate = rateService.getLastEntry(finInstrument.getSymbol()).getDate().plusDays(1);
        checkUpdateInterval(finInstrument, startDate, new DateTime());
    }

    @Transactional
    private void checkUpdateInterval(FinInstrument finInstrument, DateTime startDate, DateTime endDate) {
        if (!checkAppropriateInteral(startDate, endDate)) {
            while (!checkAppropriateInteral(startDate, endDate)) {
                endDate = startDate.plusDays(daysInterval);
                updateCurrency(finInstrument.getYahooCode(), startDate, endDate);
                startDate = rateService.getLastEntry(finInstrument.getSymbol()).getDate().plusDays(1);
                endDate = startDate.plusDays(daysInterval);
            }
        }
        updateCurrency(finInstrument.getYahooCode(), startDate, endDate);
    }

    @ResponseBody
    private void updateCurrency(String code, DateTime startDate, DateTime endDate) {
        if(Days.daysBetween(startDate, endDate).getDays() >0){
            String query = getQuery(code, startDate, endDate);
            try {
                QueryWrapper response = restTemplate.getForObject(query, QueryWrapper.class);
                rateService.appendYahooRatesToDB(response);
            } catch (Exception e) {
                if(e.getMessage() != null){
                    System.out.print(e.getMessage());
                }
            }
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
