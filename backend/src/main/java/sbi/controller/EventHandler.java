package sbi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import sbi.model.TickerData;
import sbi.model.TickerDataDTO;

@Component
public class EventHandler
{
   public static final String MESSAGE_PREFIX = "/topic";

   private final SimpMessagingTemplate websocket;

   private Gson gson = new Gson();
   
   @Autowired
   public EventHandler(SimpMessagingTemplate websocket)
   {
      this.websocket = websocket;
   }

   @SendToUser("/user/topic/getLatestTickerData")
   public void sendLatestTickerData(TickerData tickerDataETHBTC, TickerData tickerDataZECBTC, TickerData tickerDataLTCBTC) {
      List<TickerDataDTO> tickerDataArray = new ArrayList<TickerDataDTO>();
      tickerDataArray.add(new TickerDataDTO(tickerDataETHBTC));
      tickerDataArray.add(new TickerDataDTO(tickerDataZECBTC));
      tickerDataArray.add(new TickerDataDTO(tickerDataLTCBTC));
      this.websocket.convertAndSend("/topic/getLatestTickerData", gson.toJson(tickerDataArray));
   }

}
