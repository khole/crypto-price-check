package sbi.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HitbtcTicker {

   private String symbol;
   private BigDecimal last;
   
   public String getSymbol()
   {
      return symbol;
   }
   public void setSymbol(String symbol)
   {
      this.symbol = symbol;
   }
   public BigDecimal getLast()
   {
      return last;
   }
   public void setLast(BigDecimal last)
   {
      this.last = last;
   }
}
