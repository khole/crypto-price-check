package sbi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import sbi.model.Exchange;
import sbi.model.TickerData;
import sbi.repositories.TickerDataRepository;

@SpringBootApplication
@RestController
@EnableScheduling
public class Application
{

   @Autowired
   TickerDataRepository repository;

   private DynamoDBMapper dynamoDBMapper;

   @Autowired
   private AmazonDynamoDB amazonDynamoDB;

   @RequestMapping("/")
   public String home()
   {
//      try
//      {
//         dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
//         CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(TickerData.class);
//         tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
//         amazonDynamoDB.createTable(tableRequest);
//      }
//      catch (ResourceInUseException e)
//      {
//         // Do nothing, table already created
//      }
      
      List<TickerData> result = new ArrayList<TickerData>();
      repository.findByPair("xrp_jpy").ifPresent(result::add);
      for (TickerData tickerData : result)
      {
         System.out.println(tickerData.getDatetime());
         System.out.println(tickerData.getPair());
         for (Exchange exchange : tickerData.getExchanges()) {
            System.out.println(exchange.getExchange());
            System.out.println(exchange.getPrice());
         }
      }
      return "Thank you Xuatz!!!!";
   }

   public static void main(String[] args)
   {
      SpringApplication.run(Application.class, args);
   }

}
