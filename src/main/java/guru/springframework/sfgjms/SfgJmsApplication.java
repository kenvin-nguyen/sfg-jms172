package guru.springframework.sfgjms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsApplication {

    public static void main(String[] args) throws Exception{
        // 174. Set up embeded server
//        ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
//                .setPersistenceEnabled(false)
//                .setJournalDirectory("target/data/journal")
//                .setSecurityEnabled(false)
//                .addAcceptorConfiguration("invm", "vm://0")
//        );
//        server.start();

        // 181.

        SpringApplication.run(SfgJmsApplication.class, args);
    }

}
