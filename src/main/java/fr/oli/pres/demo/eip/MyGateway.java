package fr.oli.pres.demo.eip;

import fr.oli.pres.demo.entity.Author;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MyGateway {
    @Gateway(requestChannel = "inputChannel")
    void sendMessage(Author message);
}