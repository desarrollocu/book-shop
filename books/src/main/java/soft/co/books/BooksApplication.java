package soft.co.books;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import soft.co.books.configuration.storage.StorageService;

import javax.annotation.Resource;

@SpringBootApplication
public class BooksApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Resource
    StorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BooksApplication.class);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.init();
    }
}
