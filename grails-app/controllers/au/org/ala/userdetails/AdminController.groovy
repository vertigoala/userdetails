package au.org.ala.userdetails

import au.org.ala.auth.PreAuthorise

@PreAuthorise
class AdminController {

    def passwordService
    def emailService

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
           render(view:'resetPasswordForUser', model:[emailNotRecognised:true])
       }
    }
}
