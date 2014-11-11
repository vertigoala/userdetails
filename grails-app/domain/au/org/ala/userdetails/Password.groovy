package au.org.ala.userdetails

import java.sql.Timestamp

class Password implements Serializable {
    User user
    String password //md5 hash and salted
    Timestamp created
//    Timestamp expiry
    String status

    static mapping = {
        table 'passwords'
        id composite: ['user', 'password']
        user column:  'userid'
        created sqlType: 'timestamp'
//        expiry sqlType: 'DATETIME'
        version false
    }
    static constraints = {
//        expiry nullable: true
        status nullable: true
    }
}
