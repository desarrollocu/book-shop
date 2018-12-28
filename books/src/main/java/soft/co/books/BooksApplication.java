package soft.co.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BooksApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }
}
