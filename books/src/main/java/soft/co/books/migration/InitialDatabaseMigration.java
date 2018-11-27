package soft.co.books.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import soft.co.books.domain.collection.Authority;
import soft.co.books.domain.collection.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialDatabaseMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthority")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(new Authority("user-management", "Create or update user data"));
        authorityList.add(new Authority("user-list", "List users"));

        mongoTemplate.insertAll(authorityList);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUser")
    public void addUser(MongoTemplate mongoTemplate) {
        User user = new User();
        user.setFirstName("Administrator");
        user.setLastName("Admin");
        user.setUserName("admin");
        user.setEmail("admin@localhost");
        user.setActivated(true);
        user.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        user.setLangKey("en");
        user.setCreatedBy("system");
        user.setCreatedDate(Instant.now());

        user.setAuthorities(new HashSet<>(mongoTemplate.findAll(Authority.class)));
        mongoTemplate.save(user);
    }
}
