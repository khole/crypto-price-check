package sbi.collector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import sbi.model.BinanceTicker;
import sbi.model.Exchange;
import sbi.model.GeminiTicker;
import sbi.model.HitbtcTicker;
import sbi.model.TickerData;
import sbi.repositories.TickerDataRepository;

@Component
public class PriceCheckerJobLauncher
{
   @Autowired
   TickerDataRepository repository;

   final static String iso8601Format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
   
   final static String BINANCE_URL = "https://api.binance.com/api/v1/ticker/price";
   final static String HITBTC_URL = "https://api.hitbtc.com/api/2/public/ticker";
   final static String GEMINI_ETHBTC_URL = "https://api.gemini.com/v1/pubticker/ethbtc";
   final static String GEMINI_ZECBTC_URL = "https://api.gemini.com/v1/pubticker/ethbtc";
   final static String GEMINI_LTCBTC_URL = "https://api.gemini.com/v1/pubticker/ltcbtc";
   
   final static String ETHBTC = "ETHBTC";
   final static String ZECBTC = "ZECBTC";
   final static String LTCBTC = "LTCBTC";
   
   final static String HITBTC = "hitbtc";
   final static String GEMINI = "gemini";
   final static String BINANCE = "binance";
  


   @Scheduled(fixedDelay = 360000) // 6 minutes
   public void binancePriceChecker()
   {
      System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);

      long now = (new Date()).getTime();

      SimpleDateFormat dateFormatter = new SimpleDateFormat(iso8601Format);
      dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
      String datetime = dateFormatter.format(now);
      
      TickerData tickerDataETHBTC = new TickerData();
      tickerDataETHBTC.setDatetime(datetime);
      tickerDataETHBTC.setPair(ETHBTC);
      TickerData tickerDataZECBTC = new TickerData();
      tickerDataZECBTC.setDatetime(datetime);
      tickerDataZECBTC.setPair(ZECBTC);
      TickerData tickerDataLTCBTC = new TickerData();
      tickerDataLTCBTC.setDatetime(datetime);
      tickerDataLTCBTC.setPair(LTCBTC);
      
      
      // BINANCE
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<List<BinanceTicker>> response = restTemplate.exchange(
        BINANCE_URL,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<BinanceTicker>>(){});
      List<BinanceTicker> binanceTickers = response.getBody();
      for(BinanceTicker ticker : binanceTickers) {
         if(ticker.getSymbol().equals(ETHBTC)) {
            Exchange exchange = new Exchange();
            exchange.setExchange(BINANCE);
            exchange.setPrice(ticker.getPrice());
            tickerDataETHBTC.getExchanges().add(exchange);
         } else if (ticker.getSymbol().equals(ZECBTC)) {
            Exchange exchange = new Exchange();
            exchange.setExchange(BINANCE);
            exchange.setPrice(ticker.getPrice());
            tickerDataZECBTC.getExchanges().add(exchange);
         } else if (ticker.getSymbol().equals(LTCBTC)) {
            Exchange exchange = new Exchange();
            exchange.setExchange(BINANCE);
            exchange.setPrice(ticker.getPrice());
            tickerDataLTCBTC.getExchanges().add(exchange);
         }
      }
      
      // HITBTC
      RestTemplate hitbtcRestTemplate = new RestTemplate();
      ResponseEntity<List<HitbtcTicker>> hitbtcResponse = hitbtcRestTemplate.exchange(
        HITBTC_URL,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<HitbtcTicker>>(){});
      List<HitbtcTicker> hitbtcTickers = hitbtcResponse.getBody();
      for(HitbtcTicker ticker : hitbtcTickers) {
         if(ticker.getSymbol().equals(ETHBTC)) {
            Exchange exchange = new Exchange();
            exchange.setExchange(HITBTC);
            exchange.setPrice(ticker.getLast());
            tickerDataETHBTC.getExchanges().add(exchange);
         } else if (ticker.getSymbol().equals(ZECBTC)) {
            Exchange exchange = new Exchange();
            exchange.setExchange(HITBTC);
            exchange.setPrice(ticker.getLast());
            tickerDataZECBTC.getExchanges().add(exchange);
         } else if (ticker.getSymbol().equals(LTCBTC)) {
            Exchange exchange = new Exchange();
            exchange.setExchange(HITBTC);
            exchange.setPrice(ticker.getLast());
            tickerDataLTCBTC.getExchanges().add(exchange);
         }
      }
      
      // GEMINI      
      RestTemplate geminiETHBTCRestTemplate = new RestTemplate();
      GeminiTicker geminiETHBTCTicker = geminiETHBTCRestTemplate.getForObject(GEMINI_ETHBTC_URL, GeminiTicker.class);
      Exchange geminiETHBTCExchange = new Exchange();
      geminiETHBTCExchange.setExchange(GEMINI);
      geminiETHBTCExchange.setPrice(geminiETHBTCTicker.getLast());
      tickerDataETHBTC.getExchanges().add(geminiETHBTCExchange);
      
      RestTemplate geminiZECBTCRestTemplate = new RestTemplate();
      GeminiTicker geminiZECBTCTicker = geminiZECBTCRestTemplate.getForObject(GEMINI_ZECBTC_URL, GeminiTicker.class);
      Exchange geminiZECBTCExchange = new Exchange();
      geminiZECBTCExchange.setExchange(GEMINI);
      geminiZECBTCExchange.setPrice(geminiZECBTCTicker.getLast());
      tickerDataZECBTC.getExchanges().add(geminiZECBTCExchange);
      
      RestTemplate geminiLTCBTCRestTemplate = new RestTemplate();
      GeminiTicker geminiLTCBTCTicker = geminiLTCBTCRestTemplate.getForObject(GEMINI_LTCBTC_URL, GeminiTicker.class);
      Exchange geminiLTCBTCExchange = new Exchange();
      geminiLTCBTCExchange.setExchange(GEMINI);
      geminiLTCBTCExchange.setPrice(geminiLTCBTCTicker.getLast());
      tickerDataLTCBTC.getExchanges().add(geminiLTCBTCExchange);
      
      System.out.println("finished");
      repository.save(tickerDataETHBTC);
      repository.save(tickerDataZECBTC);
      repository.save(tickerDataLTCBTC);
      
   }
}
