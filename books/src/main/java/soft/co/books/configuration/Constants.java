package soft.co.books.configuration;

/**
 * Application Constants.
 */
public final class Constants {

    // Regex for acceptable userName
    public static final String USER_NAME_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String DEFAULT_LANGUAGE = "en";

    // Errors
    public static final String ERR_MAX50 = "error.E50";
    public static final String ERR_MIN1_MAX50 = "error.E51";
    public static final String ERR_USER_NAME_REGEX = "error.E52";
    public static final String ERR_MIN5_MAX254 = "error.E53";
    public static final String ERR_MIN2_MAX6 = "error.E54";
    public static final String ERR_MAX500 = "error.E55";
    public static final String ERR_NOT_NULL = "error.E56";
    public static final String ERR_MAX1000 = "error.E57";
    public static final String ERR_MIN1_MAX150 = "error.E58";
    public static final String ERR_INCORRECT_PASSWORD = "error.E59";

    private Constants() {
    }
}
