package au.org.ala.userdetails

import au.org.ala.cas.encoding.MyPasswordEncoder

class PasswordService {

    def serviceMethod() {}

    def grailsApplication

    Boolean resetPassword(user,newPassword){
       //update the password
       try {
           Password.findByUserAndStatus(user, "CURRENT").each {
               it.status = "INACTIVE"
               it.save(flush:true)
           }

           def password = new Password()
           password.user = user
           def encoder = new MyPasswordEncoder()
           encoder.setAlgorithm(grailsApplication.config.encoding.algorithm)
           encoder.setSalt(grailsApplication.config.encoding.salt)
           encoder.setBase64Encoding(true)
           password.password = encoder.encode(newPassword)
           password.created = new Date().toTimestamp()
           password.status = "CURRENT"
           password.save(flush:true)
           true
       } catch(Exception e){
           log.error(e.getMessage(),e)
           false
       }
    }
}
