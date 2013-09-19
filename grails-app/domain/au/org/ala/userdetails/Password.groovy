package au.org.ala.userdetails

class Password {
    User user
    String password //md5 hash and salted
    Date created
    Date expiry
    String status
    static constraints = {
        expiry nullable: true
        status nullable: true
    }
}
