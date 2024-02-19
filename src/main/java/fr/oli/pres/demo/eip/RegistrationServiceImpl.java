package fr.oli.pres.demo.eip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService{

     @Autowired
     private EventGateway eventGateway;

     @Override
     public void notifyObservers (Event event){
         Message<Event> message = MessageBuilder.withPayload(event)
                 .setHeader(IntegrationMessageHeaderAccessor.EXPIRATION_DATE,
                 System.currentTimeMillis() + 60 * 60 * 1000).build();
        eventGateway.publishEvent(message);
     }

}
