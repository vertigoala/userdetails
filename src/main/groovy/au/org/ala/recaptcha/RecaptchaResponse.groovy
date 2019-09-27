package au.org.ala.recaptcha

import com.squareup.moshi.Json
import groovy.transform.Canonical
import groovy.transform.CompileStatic

@Canonical
@CompileStatic
class RecaptchaResponse {

    boolean success
    String challenge_ts
    String hostname
    @Json(name = "error-codes")
    List<String> errorCodes
}
