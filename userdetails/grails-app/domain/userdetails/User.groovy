package userdetails

class User {

    String userName;
    String email;
    String firstName;
    String lastName;

    static mapping = {
        table 'users'
        userName column:  'username'
        firstName column:  'firstname'
        lastName column:  'lastname'
        id column: 'userid'
        version false
    }

    static constraints = {}
}
