package au.org.ala.userdetails

class MobileUser {

    String userName
    static hasMany = [authkeys: AuthKey]

    static constraints = {
    }
}
