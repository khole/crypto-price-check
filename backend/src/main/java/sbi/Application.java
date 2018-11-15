package sbi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import sbi.model.TickerData;
import sbi.repositories.TickerDataDao;
import sbi.repositories.TickerDataRepository;

@SpringBootApplication
@RestController
@EnableScheduling
public class Application
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

   public static void main(String[] args)
   {
      SpringApplication.run(Application.class, args);
   }

}
