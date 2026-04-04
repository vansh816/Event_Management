package net.engineeringdigest.journalApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "net.engineeringdigest.journalApp",
        "net.engineeringdigest.project.journalApp"
})
@EnableMongoRepositories(basePackages = "net.engineeringdigest.project.journalApp")
public class JournalApplication {
    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

}