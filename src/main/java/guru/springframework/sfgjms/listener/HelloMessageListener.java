package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    // @Paload: just going to tell Spring Framework to go  ahead and deserialize up component
    // The payload of that JMS message
    @JmsListener(destination = JMSConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message){

//        System.out.println("I am get a Message!!!");
//        System.out.println(helloWorldMessage);

        //throw new RuntimeException("foo");
    }

    @JmsListener(destination = JMSConfig.MY_SEND_RCVQUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                               @Headers MessageHeaders headers, Message jmsMessage,
                               org.springframework.messaging.Message springMessage) throws JMSException {

        HelloWorldMessage payloadMesg = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!!")
                .build();

        // using Spring Framework message
        //jmsTemplate.convertAndSend((Destination) springMessage.getHeaders().get("jms_replyTo"), payloadMessage);

        // We are taking the JMS reply to write of the message and then
        // adding in the payload message
        System.out.println("Received: ");
        System.out.println(jmsMessage.getBody(String.class));
        System.out.println("Rsponse: ");
        jmsTemplate.convertAndSend(jmsMessage.getJMSReplyTo(), payloadMesg);
    }
}
