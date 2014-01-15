package au.org.ala.userdetails

class UserDetailsController {

    static allowedMethods = [getUserDetails: "POST", getUserList: "POST", getUserListWithIds: "POST", getUserListFull: "POST"]

    def authorisedSystemService

    def index(){}

    def getUserDetails() {
        def user
        String userName = params.userName as String

        if (userName){
            user = User.findByUserNameOrEmail(userName, userName)
        } else {
            render status:400, text: "Missing parameter: userName"
            return
        }

        if (user == null) {
            render status:404, text: "No user found for: ${userName}"
        } else {
            render(contentType: "text/json"){ [userId:user.id.toString(), userName: user.userName, firstName: user.firstName, lastName: user.lastName] }
        }
    }

    def getUserList(){
        if(authorisedSystemService.isAuthorisedSystem(request)){
            def users = User.findAll()
            def map = [:]
            users.each {
                if(it.email){
                    map.put(it.email.toLowerCase(), it.firstName + " " + it.lastName)
                }
            }
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