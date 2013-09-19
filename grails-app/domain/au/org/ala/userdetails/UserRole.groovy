package au.org.ala.userdetails

class UserRole implements Serializable {

    User user
    Role role

    static mapping = {
        id composite: ['user', 'role']
        version false
    }

    static constraints = {
    }
    String toString(){
        role
    }

}