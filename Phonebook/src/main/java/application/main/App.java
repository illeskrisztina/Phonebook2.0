package application.main;

import application.main.model.entity.ContactInfo;
import application.main.service.Dispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class App {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Dispatcher dispatcher;
    private final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void readJsonFiles() {
        try{
            ContactInfo contact = mapper.readValue(new File(System.getProperty("user.dir") + "/Phonebook/src/main/resources/contact.json"), ContactInfo.class);

            dispatcher.addContact(contact, null);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        }
    }
}
