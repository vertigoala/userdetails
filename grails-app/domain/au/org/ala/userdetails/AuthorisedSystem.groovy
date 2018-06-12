package au.org.ala.userdetails

class AuthorisedSystem {

    String host
    String description

    static constraints = {
        host nullable: false, blank: false
        description nullable: true
    }
}
