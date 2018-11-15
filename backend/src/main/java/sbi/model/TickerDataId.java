package sbi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class TickerDataId
{
   private String ticktime;
   private String pair;
   
   @DynamoDBRangeKey
   public String getTicktime()
   {
      return ticktime;
   }

   public void setTicktime(String ticktime)
   {
      this.ticktime = ticktime;
   }

   @DynamoDBHashKey
   public String getPair()
   {
      return pair;
   }

   public void setPair(String pair)
   {
      this.pair = pair;
   }
}
