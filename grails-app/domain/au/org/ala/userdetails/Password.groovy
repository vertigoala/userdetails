package au.org.ala.userdetails

import java.sql.Timestamp

class Password implements Serializable {
    User user
    String password //md5 hash and salted
    Timestamp created
    String status

    static mapping = {
        table 'passwords'
        id composite: ['user', 'password']
        user column:  'userid'
        created sqlType: 'timestamp'
        version false
    }
    static constraints = {
        status nullable: true
    }
}
