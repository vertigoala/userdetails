package au.org.ala.userdetails

class ProfileService {

    List getUserProperty(User user, String name) {
        UserProperty.findAllByUserAndName(user, name)?:[];
    }

    UserProperty saveUserProperty(User user, String name, String value){
        UserProperty.addOrUpdateProperty(user, name, value);
    }

    List getAllAvailableProperties() {
        UserProperty.withCriteria {
            projections {
                distinct("name")
            }
            order("name")
        }
    }
}
