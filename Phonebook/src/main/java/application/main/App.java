package application.main;

import application.main.model.Entity.ContactInfo;
import application.main.service.Dispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void readJsonFiles() {
        ObjectMapper mapper = new ObjectMapper();
        Dispatcher dispatcher = new Dispatcher();

        try{
            ContactInfo contact = mapper.readValue(new File(System.getProperty("user.dir") + "/Phonebook/src/main/resources/contact.json"), ContactInfo.class);

            //Contact and address needs to be separated
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
