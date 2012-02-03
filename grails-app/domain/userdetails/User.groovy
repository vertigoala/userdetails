package userdetails

class User {

    String userName;
    String firstName;
    String lastName;

    static mapping = {
        table 'users'
        userName column:  'username'
        firstName column:  'firstname'
        lastName column:  'lastname'
        id generator:'assigned', name:'userName'
        version false

    }

    static constraints = {

    }
}
