package au.org.ala.userdetails

class ProfileController {

    def authService

    def index() {
        User user = User.get(authService.getUserId().toLong())
        render(view:"myprofile", model:[user:user])
    }
}
