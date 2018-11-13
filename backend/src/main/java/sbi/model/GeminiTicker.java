package sbi.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiTicker {

   private BigDecimal last;

   public BigDecimal getLast()
   {
      return last;
   }

   public void setLast(BigDecimal last)
   {
      this.last = last;
   }
}
