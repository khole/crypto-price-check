package sbi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import sbi.config.WebSocketConfiguration;
import sbi.model.TickerData;

@Component
@RepositoryEventHandler(TickerData.class)
public class EventHandler {

   private final SimpMessagingTemplate websocket;

   private final EntityLinks entityLinks;

   @Autowired
   public EventHandler(SimpMessagingTemplate websocket, 
            EntityLinks entityLinks) {
      this.websocket = websocket;
      this.entityLinks = entityLinks;
   }

   @HandleAfterCreate
   public void newEmployee(TickerData tickerData) {
      this.websocket.convertAndSend(
        WebSocketConfiguration.MESSAGE_PREFIX + "/newEmployee", getPath(tickerData));
   }

   @HandleAfterDelete
   public void deleteEmployee(TickerData tickerData) {
      this.websocket.convertAndSend(
        WebSocketConfiguration.MESSAGE_PREFIX + "/deleteEmployee", getPath(tickerData));
   }

   @HandleAfterSave
   public void updateEmployee(TickerData tickerData) {
      this.websocket.convertAndSend(
        WebSocketConfiguration.MESSAGE_PREFIX + "/updateEmployee", getPath(tickerData));
   }

   /**
    * Take an {@link TickerData} and get the URI using 
    * Spring Data REST's {@link EntityLinks}.
    *
    * @param tickerData
    */
   private String getPath(TickerData tickerData) {
     return this.entityLinks.linkForSingleResource(tickerData.getClass(),
         tickerData.getDatetime()).toUri().getPath();
   }

}