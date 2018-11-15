package sbi.model;

import java.util.ArrayList;
import java.util.List;

public class TickerDataDTO
{
   private String ticktime;
   private String pair;
   private List<Exchange> exchanges = new ArrayList<Exchange>();

   public TickerDataDTO(TickerData tickerData) {
      this.ticktime = tickerData.getTicktime();
      this.pair = tickerData.getPair();
      this.exchanges = tickerData.getExchanges();
   }
   
   public String getTicktime()
   {
      return this.ticktime;
   }

   public void setTicktime(String ticktime)
   {
      this.ticktime = ticktime;
   }

   public String getPair()
   {
      return pair;
   }

   public void setPair(String pair)
   {
      this.pair = pair;
   }

   public List<Exchange> getExchanges()
   {
      return exchanges;
   }

   public void setExchanges(List<Exchange> exchanges)
   {
      this.exchanges = exchanges;
   }
}
