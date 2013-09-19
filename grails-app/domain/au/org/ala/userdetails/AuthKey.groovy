package au.org.ala.userdetails

class AuthKey {

    String key
    MobileUser mobileUser

    static constraints = {}

    static mapping = {
        key column: "auth_key"
    }
}
