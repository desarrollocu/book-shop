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
        authorityList.add(new Authority("user-list", "Users list"));
        authorityList.add(new Authority("book-list", "Books list"));
        authorityList.add(new Authority("book-management", "List users"));
        authorityList.add(new Authority("author-management", "Create or update author data"));
        authorityList.add(new Authority("author-list", "Authors list"));
        authorityList.add(new Authority("editor-management", "Create or update editor data"));
        authorityList.add(new Authority("editor-list", "Editors list"));
        authorityList.add(new Authority("publisher-management", "Create or update publisher data"));
        authorityList.add(new Authority("publisher-list", "Publishers list"));
        authorityList.add(new Authority("descriptor-management", "Create or update descriptor data"));
        authorityList.add(new Authority("descriptor-list", "Descriptors list"));
        authorityList.add(new Authority("country-management", "Create or update country data"));
        authorityList.add(new Authority("country-list", "Countries list"));
        authorityList.add(new Authority("magazine-management", "Create or update magazine data"));
        authorityList.add(new Authority("magazine-list", "Magazines list"));

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
