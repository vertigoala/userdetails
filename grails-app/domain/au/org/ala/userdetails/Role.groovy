package au.org.ala.userdetails

class Role implements Serializable {

    String role
    String description

    static mapping = {
        id (generator:'assigned', column:'role', type:'string' , name: 'role')
        version false
    }

    String toString(){
        role
    }

    static constraints = {
        role nullable: false, blank: false
        description nullable:true
    }
}
