package au.org.ala.auth

/**
 * Checked exception to avoid automatic transaction rollback
 */
class PasswordResetFailedException extends Exception {

    public PasswordResetFailedException(Throwable cause) {
        super(cause)
    }

}
