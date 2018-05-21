package au.org.ala.userdetails

import java.sql.Timestamp

/*
+----------+--------------+------+-----+---------------------+-----------------------------+
| Field    | Type         | Null | Key | Default             | Extra                       |
+----------+--------------+------+-----+---------------------+-----------------------------+
| userid   | bigint(20)   | NO   | PRI | NULL                |                             |
| password | varchar(255) | NO   | PRI | NULL                |                             |
| type     | varchar(255) | NO   |     | NULL                |                             |
| created  | timestamp    | NO   |     | CURRENT_TIMESTAMP   |                             |
| expiry   | timestamp    | YES  |     | NULL                |                             |
| status   | varchar(10)  | NO   |     | NULL                |                             |
+----------+--------------+------+-----+---------------------+-----------------------------+
 */
class Password implements Serializable {
    static belongsTo = [user: User]
    String password //encoded
    String type
    Timestamp created
    Timestamp expiry
    String status

    static mapping = {
        table 'passwords'
        id composite: ['user', 'password']
        user column:  'userid'
        created sqlType: 'timestamp'
        expiry sqlType: 'timestamp'
        version false
    }
    static constraints = {
        status nullable: false
        expiry nullable: true
    }
}
