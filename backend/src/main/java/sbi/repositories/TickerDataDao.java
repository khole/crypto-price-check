package sbi.repositories;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import sbi.model.TickerData;

@Component
public class TickerDataDao
{
   @Autowired
   private AmazonDynamoDB amazonDynamoDB;

   private DynamoDBMapper dynamoDBMapper;

   public List<TickerData> getCurrentTickerData()
   {
      System.out.println("getCurrentTickerData");

      DynamoDBQueryExpression<TickerData> queryExpression = new DynamoDBQueryExpression<TickerData>().withLimit(1);

      List<TickerData> tickerDataList = this.dynamoDBMapper.query(TickerData.class, queryExpression);
      return tickerDataList;
   }

   public List<TickerData> getTickerDataInLast7Days()
   {
      System.out.println("getTickerDataInLast7Days");
      this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
      
      long sevenDaysAgoMilli = (new Date()).getTime() - (7L * 24L * 60L * 60L * 1000L);
      Date sevenDaysAgo = new Date();
      sevenDaysAgo.setTime(sevenDaysAgoMilli);
      SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
      String sevenDaysAgoStr = dateFormatter.format(sevenDaysAgo);

      Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
      eav.put(":val1", new AttributeValue().withS(sevenDaysAgoStr.toString()));
      eav.put(":val2", new AttributeValue().withS("ETHBTC"));

      DynamoDBQueryExpression<TickerData> queryExpression = new DynamoDBQueryExpression<TickerData>().withKeyConditionExpression("tickerTimestamp > :val1 and pair = :val2")
                                                                                                     .withExpressionAttributeValues(eav);

      List<TickerData> tickerDataList = this.dynamoDBMapper.query(TickerData.class, queryExpression);
      return tickerDataList;

   }
}
