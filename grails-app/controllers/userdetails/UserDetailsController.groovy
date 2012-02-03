package userdetails

class UserDetailsController {

    static allowedMethods = [getUserDetails: "POST"]

    def getUserDetails() { 
        println("Getting details for username: " + params.userName)
        
        User user = User.findByUserName(params.userName)
        if (user == null) {
            println("No record for username:" + params.userName)
            response.sendError(403)
        } else {
            println("Username: " + user.userName + ", First name: " + user.firstName + ", Last name: " + user.lastName)
            [userName: user.userName, firstName: user.firstName, lastName: user.lastName]
        }
    }
}
