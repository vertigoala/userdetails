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
        id (generator:'identity', column:'userid', type:'long')
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

    static List<String[]> findNameAndEmailWhereEmailIsNotNull() {
        withCriteria {
            isNotNull('email')
            projections {
                property('email')
                property('firstName')
                property('lastName')
            }
        }
    }

    static List<String[]> findIdFirstAndLastName() {
        withCriteria {
            projections {
                property('id')
                property('firstName')
                property('lastName')
            }
        }
    }

    static List<String[]> findUserDetails() {
        withCriteria {
            projections {
                property('id')
                property('firstName')
                property('lastName')
                property('userName')
                property('email')
            }
        }
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
