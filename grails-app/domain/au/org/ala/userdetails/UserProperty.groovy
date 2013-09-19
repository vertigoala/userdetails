package au.org.ala.userdetails

class UserProperty implements Serializable {

    User user
    String property
    String value

    static mapping = {
        table 'profiles'
        id composite: ['user', 'property']
        user column:  'userid'
        version false
    }
    static constraints = {
    }

    String toString(){
        property + " : " + value
    }
}
