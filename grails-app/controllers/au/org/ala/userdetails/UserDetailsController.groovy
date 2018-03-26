package au.org.ala.userdetails

import au.org.ala.userdetails.marshaller.UserMarshaller
import grails.converters.JSON

class UserDetailsController {

    static allowedMethods = [getUserDetails: "POST", getUserList: "POST", getUserListWithIds: "POST", getUserListFull: "POST", getUserDetailsFromIdList: "POST", findUser: "GET"]

    def index() {}

    def getUserDetails() {

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
            String jsonConfig = includeProps ? UserMarshaller.WITH_PROPERTIES_CONFIG : null
            try {
                JSON.use(jsonConfig)
                render user as JSON
            }
            finally {
                JSON.use(null) // resets to default config
            }
        }

    }

    def getUserList() {
        def users = User.findNameAndEmailWhereEmailIsNotNull()
        def map = users.collectEntries { [(it[0].toLowerCase()): "${it[1]?:""} ${it[2]?:""}"]}
        render(map as JSON, contentType: "text/json")
    }

    def getUserListWithIds() {
        def users = User.findIdFirstAndLastName()
        def map = users.collectEntries { [(it[0]), "${it[1]?:""} ${it[2]?:""}"] }
        render(map as JSON, contentType: "text/json")
    }

    def getUserListFull() {
        def details = User.findUserDetails().collect {
            [id: it[0], firstName: it[1]?:"", lastName: it[2]?:"", userName: it[3]?:"", email: it[4]?:""]
        }
        render(details as JSON, contentType: "text/json")
    }

    def getUserDetailsFromIdList() {

        def req = request.JSON
        def includeProps = req?.includeProps ?: params.getBoolean('includeProps', false)

        if (req && req.userIds) {

            try {
                List<Long> idList = req.userIds.collect { userId -> userId as long }

                def c = User.createCriteria()
                def results = c.list() {
                    'in'("id", idList)
                }
                String jsonConfig = includeProps ? UserMarshaller.WITH_PROPERTIES_CONFIG : null
                try {

                    JSON.use(jsonConfig)

                    def resultsMap = [users:[:], invalidIds:[], success: true]
                    results.each { user ->
                        resultsMap.users[user.id] = user
                    }

                    idList.each {
                        if (!resultsMap.users[it]) {
                            resultsMap.invalidIds << it
                        }
                    }

                    render resultsMap as JSON
                }
                finally {
                    JSON.use(null) // Reset to default
                }
            } catch (Exception ex) {
                render([success: false, message: "Exception: ${ex.toString()}"] as JSON, contentType: "text/json")
            }
        } else {
            render([success: false, message: "Body must contain JSON map payload with 'userIds' key that contains a list of user ids"] as JSON, contentType: "text/json")
        }
    }

    def findUser(Integer max) {
        if(params.q){
            def q = "%"+ params.q + "%"
            def userList = User.findAllByEmailLikeOrLastNameLikeOrFirstNameLike(q,q,q)
            def result = [results: userList, total: userList.size(), q:params.q]
            render result as JSON
        } else {
            params.max = Math.min(max ?: 20, 5000)
            def result =[results: User.list(params), total: User.count()]
            render result as JSON
        }
    }
}