package sbi.model;

import java.math.BigDecimal;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Exchange
{
   private BigDecimal price;
   private String exchange;

   @DynamoDBAttribute
   public BigDecimal getPrice()
   {
      return price;
   }

   public void setPrice(BigDecimal price)
   {
      this.price = price;
   }

   @DynamoDBAttribute
   public String getExchange()
   {
      return exchange;
   }

   public void setExchange(String exchange)
   {
      this.exchange = exchange;
   }
}
