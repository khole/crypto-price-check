package sbi.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "TickerData")
public class TickerData
{
   @Id
   private TickerDataId tickerDataId;
   
   private List<Exchange> exchanges = new ArrayList<Exchange>();

   @DynamoDBRangeKey(attributeName = "ticktime")
   public String getTicktime()
   {
      return tickerDataId != null ? tickerDataId.getTicktime() : null;
   }

   public void setTicktime(String ticktime)
   {
      if (tickerDataId == null) {
         tickerDataId = new TickerDataId();
      }
      tickerDataId.setTicktime(ticktime);
   }

   @DynamoDBHashKey(attributeName = "pair")
   public String getPair()
   {
      return tickerDataId != null ? tickerDataId.getPair() : null;
   }

   public void setPair(String pair)
   {
      if (tickerDataId == null) {
         tickerDataId = new TickerDataId();
      }
      tickerDataId.setPair(pair);
   }

   @DynamoDBAttribute(attributeName = "exchanges")
   public List<Exchange> getExchanges()
   {
      return exchanges;
   }

   public void setExchanges(List<Exchange> exchanges)
   {
      this.exchanges = exchanges;
   }
}
