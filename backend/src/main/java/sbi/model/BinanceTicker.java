package sbi.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceTicker {

   private String symbol;
   private BigDecimal price;
   public String getSymbol()
   {
      return symbol;
   }
   public void setSymbol(String symbol)
   {
      this.symbol = symbol;
   }
   public BigDecimal getPrice()
   {
      return price;
   }
   public void setPrice(BigDecimal price)
   {
      this.price = price;
   }
}
