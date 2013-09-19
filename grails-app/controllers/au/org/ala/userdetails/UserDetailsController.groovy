package au.org.ala.userdetails

class UserDetailsController {

    static allowedMethods = [getUserDetails: "POST", getUserList: "POST", getUserListWithIds: "POST", getUserListFull: "POST"]

    def authorisedSystemService

    def index(){}

    def getUserDetails() {
        if(params.userName){
            User user = User.findByUserName(params.userName)
            if (user == null) {
                response.sendError(404)
            } else {
                render(contentType: "text/json"){ [userId:user.id.toString(), userName: user.userName, firstName: user.firstName, lastName: user.lastName] }
            }
        } else {
            response.sendError(400, "Missing parameter userName")
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