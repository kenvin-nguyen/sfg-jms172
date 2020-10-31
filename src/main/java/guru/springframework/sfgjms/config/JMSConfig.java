package guru.springframework.sfgjms.config;

import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * video 176: Messaging Convert Configuration
 */
@Configuration
public class JMSConfig {

    public static final String MY_QUEUE = "my-hello-world";
    public static final String MY_SEND_RCVQUEUE = "replybacktome";

    // we set up the message converter
    // that is going to be providing a message converter for us
    // That's configured by Jackson and Spring Boot
    // Spring Boot is going to be creating a Jackson object mapper for us and configuring
    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
