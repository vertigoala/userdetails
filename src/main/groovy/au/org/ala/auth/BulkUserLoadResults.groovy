package au.org.ala.auth

/**
 * Created by baird on 25/11/2013.
 */
class BulkUserLoadResults {
    boolean success
    String message
    List failedRecords = []
    List warnings = []
    int userAccountsCreated = 0
}
