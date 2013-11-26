package au.org.ala.userdetails

import au.org.ala.auth.PreAuthorise
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

@PreAuthorise
class AdminController {

    def passwordService
    def emailService
    def userService

    def index() {}

    def resetPasswordForUser(){
    }

    def sendPasswordResetEmail(){

       def user = User.findByEmail(params.email)
       if(user){
           def password = passwordService.generatePassword(user)
           //email to user
           emailService.sendGeneratedPassword(user, password)
           render(view:'userPasswordResetSuccess', model:[email:params.email])
       } else {
           render(view:'resetPasswordForUser', model:[email:params.email, emailNotRecognised:true])
       }
    }

    def bulkUploadUsers() {
    }

    def loadUsersCSV() {
        if(request instanceof MultipartHttpServletRequest) {
            MultipartFile f = ((MultipartHttpServletRequest) request).getFile('userList')
            if (f && f.size > 0) {
                def allowedMimeTypes = ['text/plain', 'text/csv']
                if (!allowedMimeTypes.contains(f.getContentType())) {
                    flash.message = "The file must be one of: ${allowedMimeTypes}. Submitted file is of type ${f.getContentType()}"
                    redirect(action:"bulkUploadUsers")
                    return
                }

                def results = userService.bulkRegisterUsersFromFile(f.inputStream, (boolean) params.firstRowHasFieldNames, params.primaryUsage as String)
                render(view:'loadUsersResults', model:[results: results])
                return
            } else {
                flash.message = "You must select a file to upload!"
            }
        }
        redirect(action:"bulkUploadUsers")
    }

}
