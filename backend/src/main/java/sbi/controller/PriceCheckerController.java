package sbi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import sbi.model.TickerData;
import sbi.repositories.TickerDataDao;
import sbi.repositories.TickerDataRepository;

@RestController
public class PriceCheckerController
{
   @Autowired
   TickerDataRepository repository;

   @Autowired
   TickerDataDao tickerDataDao;

   private DynamoDBMapper dynamoDBMapper;

   @Autowired
   private AmazonDynamoDB amazonDynamoDB;

   @RequestMapping("/")
   public String home()
   {
      // try
      // {
      // dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
      // CreateTableRequest tableRequest =
      // dynamoDBMapper.generateCreateTableRequest(TickerData.class);
      // tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
      // amazonDynamoDB.createTable(tableRequest);
      // }
      // catch (ResourceInUseException e)
      // {
      // // Do nothing, table already created
      // }

      return "Table Created";
   }

   @RequestMapping("/current_prices")
   public List<TickerData> getCurrentPrices()
   {      
      TickerData first = repository.findFirstByPairOrderByTicktimeDesc("ETHBTC");
      String ticktime = first.getTicktime();
      Iterable<TickerData> iterable =repository.findByTicktime(ticktime);
      List<TickerData> result = StreamSupport.stream(iterable.spliterator(), false)
            .collect(Collectors.toList()); 
      return result;
   }
   
   @RequestMapping("/last_thirty_minutes_data")
   public List<TickerData> getLastThirtyMinutesData(@RequestParam("pair") String pair)
   {      
      long oneHourAgo = (new Date()).getTime() - (1800000);
      SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
      String oneHourAgoStr = dateFormatter.format(oneHourAgo);
      
      Iterable<TickerData> iterable =repository.findByPairAndTicktimeGreaterThan(pair, oneHourAgoStr);
      List<TickerData> result = StreamSupport.stream(iterable.spliterator(), false)
            .collect(Collectors.toList()); 
      return result;
   }
   
   @RequestMapping("/last_five_data")
   public List<TickerData> getLastFiveData(@RequestParam("pair") String pair)
   {      
      Iterable<TickerData> iterable =repository.findFirst5ByPairOrderByTicktimeDesc(pair);
      List<TickerData> result = StreamSupport.stream(iterable.spliterator(), false)
            .collect(Collectors.toList()); 
      return result;
   }
}
