package userdetails

class UserDetailsController {

    static allowedMethods = [getUserDetails: "POST", getUserByIdOrEmail: "GET", getUserList: "POST", getUserListWithIds: "POST"]
    def authorisedSystemService

    def getUserDetails() { 
       // println("Getting details for username: " + params.userName)
        
        User user = User.findByUserName(params.userName)
        if (user == null) {
         //   println("No record for username:" + params.userName)
            response.sendError(404)
        } else {
           // println("Username: " + user.userName + ", First name: " + user.firstName + ", Last name: " + user.lastName)
            [userName: user.userName, firstName: user.firstName, lastName: user.lastName]
        }
    }

    def getUserByIdOrEmail(){
        User user = User.findByUserNameOrId(params.q)
        if (user == null) {
         //   println("No record for username:" + params.userName)
            response.sendError(404)
        } else {
        //    println("Username: " + user.userName + ", First name: " + user.firstName + ", Last name: " + user.lastName)
            render(contentType: "text/json"){
                [userName: user.userName, firstName: user.firstName, lastName: user.lastName]
            }
        }
    }

    def getUserList(){
        if(authorisedSystemService.isAuthorisedSystem(request)){
            def users = User.findAll()
            def map = [:]
            users.each { map.put(it.email.toLowerCase(), it.firstName + " "+ it.lastName) }
            render(contentType: "text/json"){ map }
        } else {
            response.sendError(403)
        }
    }

    def getUserListWithIds(){
        if(authorisedSystemService.isAuthorisedSystem(request)){
            def users = User.findAll()
            def map = [:]
            users.each { map.put(it.id, it.firstName + " "+ it.lastName) }
            render(contentType: "text/json"){ map }
        } else {
            response.sendError(403)
        }
    }

    def getUserListFull(){
        if(authorisedSystemService.isAuthorisedSystem(request)){
            render(contentType: "text/json"){ User.findAll() }
        } else {
            response.sendError(403)
        }
    }
}