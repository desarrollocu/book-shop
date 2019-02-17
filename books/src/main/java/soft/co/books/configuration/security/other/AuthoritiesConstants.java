package soft.co.books.configuration.security.other;


/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    //User
    public static final String USER_MANAGEMENT = "user-management";
    public static final String USER_LIST = "user-list";
    //Author
    public static final String AUTHOR_MANAGEMENT = "author-management";
    public static final String AUTHOR_LIST = "author-list";
    //Editor
    public static final String EDITOR_MANAGEMENT = "editor-management";
    public static final String EDITOR_LIST = "editor-list";
    //Topic
    public static final String TOPIC_MANAGEMENT = "topic-management";
    public static final String TOPIC_LIST = "topic-list";
    //Book
    public static final String BOOK_MANAGEMENT = "book-management";
    public static final String BOOK_LIST = "book-list";
    public static final String BOOK_TRACE = "book-trace";
    //Magazine
    public static final String MAGAZINE_MANAGEMENT = "magazine-management";
    public static final String MAGAZINE_LIST = "magazine-list";
    //UIData
    public static final String UIDATA_MANAGEMENT = "info-management";
    public static final String UIDATA_LIST = "contact-list";
    //Sale
    public static final String SALE_MANAGEMENT = "sale-management";
    public static final String SALE_LIST = "sale-list";
    //Trace
    public static final String TRACE_LIST = "trace-list";

    //Other
    public static final String ANONYMOUS = "ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
