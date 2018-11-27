package soft.co.books.configuration.database;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoRepositories("soft.co.books.domain.repository")
@Import(value = MongoAutoConfiguration.class)
//@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class MongoConfiguration {

    private final Logger log = LoggerFactory.getLogger(MongoConfiguration.class);

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

//    @Bean
//    public MongoCustomConversions customConversions() {
//        List<Converter<?, ?>> converters = new ArrayList<>();
////        converters.add(DateToZonedDateTimeConverter.INSTANCE);
//        converters.add(ZonedDateTimeToDateConverter.INSTANCE);
//        return new MongoCustomConversions(converters);
//    }

    @Bean
    public Mongobee mongobee(MongoClient mongoClient, MongoTemplate mongoTemplate, MongoProperties mongoProperties) {
        log.debug("Configuring Mongobee");
        Mongobee mongobee = new Mongobee(mongoClient);
        mongobee.setDbName(mongoProperties.getMongoClientDatabase());
        mongobee.setMongoTemplate(mongoTemplate);

        // package to scan for migrations
        mongobee.setChangeLogsScanPackage("soft.co.books.migration");
        mongobee.setEnabled(true);
        return mongobee;
    }
}
