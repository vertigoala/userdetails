package au.org.ala.userdetails

import au.org.ala.userdetails.marshaller.UserMarshaller
import grails.converters.JSON

class UserDetailsController {

    static allowedMethods = [getUserDetails: "POST", getUserList: "POST", getUserListWithIds: "POST", getUserListFull: "POST", getUserDetailsFromIdList: "POST"]

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

    def getUserList(){
        def users = User.executeQuery('select email, firstName, lastName from User where email is not null')
        def map = [:]
        users.each {
            if(it[0]){
                map.put(it[0].toLowerCase(), it[1]?:"" + " " + it[2]?:"")
            }
        }
        render(contentType: "text/json"){ map }
    }

    def getUserListWithIds(){
        def users = User.executeQuery('select id, firstName, lastName from User')
        def map = [:]
        users.each { map.put(it[0], it[1]?:"" + " " + it[2]?:"") }
        render(contentType: "text/json"){ map }
    }

    def getUserListFull(){
        render(contentType: "text/json") {
            //limit fields
            User.executeQuery('select id, firstName, lastName, userName, email from User').collect {
                [id: it[0], firstName: it[1]?:"", lastName: it[2]?:"", userName: it[3]?:"", email: it[4]?:""]
            }
        }
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
                render(contentType: "text/json") { [success: false, message: "Exception: ${ex.toString()}"] }
            }
        } else {
            render(contentType: "text/json") { [success: false, message: "Body must contain JSON map payload with 'userIds' key that contains a list of user ids"] }
        }

    }
}