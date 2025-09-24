package application.main.model.database.data;

import application.main.model.entity.ContactInfo;
import application.main.service.Dispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoadJsonFiles {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Dispatcher dispatcher;
    private final Logger logger = LoggerFactory.getLogger(LoadJsonFiles.class);

    @EventListener(ApplicationReadyEvent.class)
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