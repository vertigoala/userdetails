package au.org.ala.userdetails

class UserProperty implements Serializable {

    User user
    String property
    String value

    static def addOrUpdateProperty(user, name, value){

        def up = UserProperty.findByUserAndProperty(user, name)
        if(!up){
           up = new UserProperty(user:user, property:name, value:value)
        } else {
           up.value = value
        }
        up.save(flush:true)
        up
    }

    static mapping = {
        table 'profiles'
        id composite: ['user', 'property']
        user column:  'userid'
        version false
        value sqlType: 'text'
    }
    static constraints = {
        value nullable: false, blank: true
        property nullable: false, blank: false
    }

    String toString(){
        property + " : " + value
    }
}
