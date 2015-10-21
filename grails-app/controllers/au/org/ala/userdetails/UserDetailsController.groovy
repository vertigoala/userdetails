package au.org.ala.userdetails

class UserDetailsController {

    static allowedMethods = [getUserDetails: "POST", getUserList: "POST", getUserListWithIds: "POST", getUserListFull: "POST", getUserDetailsFromIdList: "POST"]

    def authorisedSystemService

    def index() {}

    def getUserDetails() {

        if (authorisedSystemService.isAuthorisedSystem(request)){
            def user
            String userName = params.userName as String
            def includeProps = params.getBoolean('includeProps', false)
            if (userName) {
                if (userName.isLong()) {
                    user = User.findById(userName.toLong())
                } else {
                    user = User.findByUserNameOrEmail(userName, userName)
                }
            } else {
                render status:400, text: "Missing parameter: userName"
                return
            }

            if (user == null) {
                render status:404, text: "No user found for: ${userName}"
            } else {
                render(contentType: "text/json") { makeUserdetailsMap(user, includeProps) }
            }
        } else {
            response.sendError(403)
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

    private static Map makeUserdetailsMap(User user, boolean includeProps = false) {
        final results = [
                            userId:user.id.toString(),
                            userName: user.userName,
                            firstName: user.firstName,
                            lastName: user.lastName,
                            email: user.email,
                            role: user.getUserRoles()?.collect{ it.toString(); }
        ]
        if (includeProps) {
            results['props'] = user.propsAsMap()
        }
        results
    }

    def getUserDetailsFromIdList() {
        if(authorisedSystemService.isAuthorisedSystem(request)){
            def req = request.JSON
            def includeProps = req?.includeProps ?: params.getBoolean('includeProps', false)

            if (req && req.userIds) {

                try {
                    List<Long> idList = req.userIds.collect { userId -> userId as long }

                    def c = User.createCriteria()
                    def results = c.list() {
                        'in'("id", idList)
                    }

                    def resultsMap = [users:[:], invalidIds:[], success: true]
                    results.each { user ->
                        resultsMap.users[user.id] = makeUserdetailsMap(user, includeProps)
                    }

                    idList.each {
                        if (!resultsMap.users[it]) {
                            resultsMap.invalidIds << it
                        }
                    }

                    render(contentType: "text/json") { resultsMap }
                } catch (Exception ex) {
                    render(contentType: "text/json") { [success: false, message: "Exception: ${ex.toString()}"] }
                }
            } else {
                render(contentType: "text/json") { [success: false, message: "Body must contain JSON map payload with 'userIds' key that contains a list of user ids"] }
            }
        } else {
            response.sendError(403)
        }
    }
}