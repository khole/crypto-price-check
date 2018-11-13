package sbi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class TickerDataId
{
   private String datetime;
   private String pair;

   @DynamoDBHashKey
   public String getDatetime()
   {
      return datetime;
   }

   public void setDatetime(String datetime)
   {
      this.datetime = datetime;
   }

   @DynamoDBRangeKey
   public String getPair()
   {
      return pair;
   }

   public void setPair(String pair)
   {
      this.pair = pair;
   }
}
