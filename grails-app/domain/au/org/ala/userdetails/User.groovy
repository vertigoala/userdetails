package au.org.ala.userdetails

import java.sql.Timestamp

class User implements Serializable {

    static hasMany =  [userRoles:UserRole, userProperties:UserProperty]

    String firstName
    String lastName

    String userName
    String email

    Timestamp created

    Boolean activated
    Boolean locked

    String tempAuthKey

    static mapping = {
        table 'users'
        id (generator:'increment', column:'userid', type:'long')
        userName column:  'username'
        firstName column:  'firstname'
        lastName column:  'lastname'
        created type: Timestamp, sqlType: "timestamp"
        version false
    }

    static constraints = {
        email nullable: true
        firstName  nullable: true
        lastName  nullable: true
        activated nullable: false
        locked nullable: false
        created nullable: false
        tempAuthKey nullable: true
    }

   def beforeInsert() {
        created = new Date().toTimestamp()
   }

    def propsAsMap(){
        def map = [:]
        this.getUserProperties().each {
            map.put(it.property, it.value)
        }
        map
    }

    String toString(){
        firstName + " " + lastName + " <" +email +">"
    }
}
