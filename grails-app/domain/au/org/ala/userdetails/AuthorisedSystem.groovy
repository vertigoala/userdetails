package au.org.ala.userdetails

class AuthorisedSystem {

    String host
    String description

    static constraints = {
        description nullable: true
    }
}
