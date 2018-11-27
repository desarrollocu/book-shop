package soft.co.books.configuration;

/**
 * Application Constants.
 */
public final class Constants {

    // Regex for acceptable userName
    public static final String USER_NAME_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String DEFAULT_LANGUAGE = "en";

    // Errors
    public static final String ERR_MAX50 = "E50";
    public static final String ERR_MIN1_MAX50 = "E51";
    public static final String ERR_USER_NAME_REGEX = "E52";
    public static final String ERR_MIN5_MAX254 = "E53";
    public static final String ERR_MIN2_MAX6 = "E54";
    public static final String ERR_MAX500 = "E55";
    public static final String ERR_NOT_NULL = "E56";
    public static final String ERR_MAX1000 = "E57";
    public static final String ERR_MIN1_MAX150 = "E58";
    public static final String ERR_INCORRECT_PASSWORD = "E59";

    private Constants() {
    }
}
