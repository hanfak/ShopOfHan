package hanfak.shopofhan.infrastructure.database.jdbc;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneId.systemDefault;

//TODO: MON-256 these methods should probably only be used in the EnhancedResultSet and EnhancedPreparedStatement
//If that is not the case, it means that we need to migrate Readers and Writers into repositories
public class OracleColumnAdapters {
//TODO test
    /**
     * We use this character to represent the empty string "" in Oracle,
     * because we want NOT NULL columns but Oracle stores "" as NULL.
     */
    public static final String EMPTY_STRING_REPRESENTATION = "\t";

    public static boolean adaptIntToBoolean(int persistedIntegerThatRepresentsABoolean) {
        return persistedIntegerThatRepresentsABoolean != 0;
    }

    public static int adaptBooleanToInt(boolean booleanThatWillBePersistedAsAnInt) {
        return booleanThatWillBePersistedAsAnInt ? 1 : 0;
    }

    public static Timestamp adaptZonedDateTimeToTimeStamp(ZonedDateTime zonedDateTime) {
        return Timestamp.from(zonedDateTime.toInstant());
    }

    public static ZonedDateTime adaptTimeStampToZonedDateTime(Timestamp timestamp) {
        return ofEpochMilli(timestamp.getTime()).atZone(systemDefault());
    }

    public static String adaptFromEmptyStringRepresentation(String string) {
        if (EMPTY_STRING_REPRESENTATION.equals(string)) {
            return "";
        } else {
            return string;
        }
    }

    public static String adaptToEmptyStringRepresentation(String string) {
        if (string.isEmpty()) {
            return EMPTY_STRING_REPRESENTATION;
        } else {
            return string;
        }
    }
}
