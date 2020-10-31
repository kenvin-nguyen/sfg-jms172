package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, message);
    }

    // Implement a sendAndReceive Message sender
    @Scheduled(fixedRate = 2000)
    public void sendAndRecieveMessage() throws JMSException{

        //1. Prepare the message
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello!")
                .build();

        // 2. Using JMS template sendAndReceive to send and receive message
        //    This JMS send and receive responses back with a receive message
        Message receviedMsg = jmsTemplate.sendAndReceive(JMSConfig.MY_SEND_RCVQUEUE, new MessageCreator() {

            // 3. create new instance of message from creator
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    // 4. in the constrcutor we're providing a JSON value through the object mapper
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));

                    // 5. we given _tpe and th fully qualified class name of that JSON payload
                    // So, on the receiving side we can deserialize it back into a Java object
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");

                    return helloMessage;
                }catch(JsonProcessingException e){
                    throw new JMSException("boom");
                    //e.printStackTrace();
                }

            }
        });
        // 6. we are getting the body of the message that was sent to us on the temporary reply Queue
        String replyMsg = receviedMsg.getBody(String.class);
        System.out.println(replyMsg);
    }
}
