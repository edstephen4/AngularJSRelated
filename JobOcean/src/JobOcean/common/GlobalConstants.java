/* ------------------------------------------------------------------------------
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Copyright (C) Rococo Global Technologies, Inc - All Rights Reserved 2016
 * --------------------------------------------------------------------------- */
package JobOcean.common;

/**
 * Contains the constants that will be used by the application.
 * @author Lehmar Cabrillos
 * @version 0.01
 * Version History
 * [03/02/2016] 0.01 – Lehmar Cabrillos  – Initial codes.
 */
public class GlobalConstants {
    /**
     * Error message for unable to add entry.
     */
    public static final String ERR_ENTRY_CANNOT_ADD = "Unable to add entry. Please try again.";

    /**
     * Error message for unable to update entry.
     */
    public static final String ERR_ENTRY_CANNOT_UPDATE = "Unable to update entry. Please try again.";

    /**
     * Error message for unable to delete entry.
     */
    public static final String ERR_ENTRY_CANNOT_DELETE = "Unable to delete entry. Please try again.";

    /**
     * Error message for database/datastore exceptions.
     */
    public static final String ERR_DB_EXCEPTION = "DB Exception Occurred. Please Contact Administrator.";

    /**
     * Error message for missing entry.
     */
    public static final String ERR_ENTRY_NOT_FOUND = "Entry not found in datastore, please try refreshing the list.";

    /**
     * Prefix for Server Controller Error.
     */
    public static final String ERR_SERVER_CONTROLLER_PREFIX = "Server Controller Error: ";
    
    /**
     * Content Type - JSON.
     */
    public static final String SYS_CONTENT_TYPE_JSON = "application/json";
}
