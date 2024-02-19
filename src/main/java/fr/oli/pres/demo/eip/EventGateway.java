package fr.oli.pres.demo.eip;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(name="eventGateway", defaultRequestChannel = "eventChannel")
public interface EventGateway {
    @Gateway
    void publishEvent(Message<Event> message);
}
