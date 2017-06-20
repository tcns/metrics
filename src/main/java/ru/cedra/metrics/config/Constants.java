package ru.cedra.metrics.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";

    public  static DateFormat DATE_F = new SimpleDateFormat("MMddyyyy");
    public static final String ANONYMOUS_USER = "anonymoususer";

    private Constants() {
    }
}
