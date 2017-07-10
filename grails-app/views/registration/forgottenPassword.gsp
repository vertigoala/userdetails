<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Reset my password</title>
</head>
<body>

<div class="row">
    <h1>Reset my password</h1>
    <div class="row">
        <div class="col-md-6">

            <g:if test="${captchaInvalid}">
            <p class="well text-danger">
                Your input did not match the text in the image. Please try again.
            </p>
            </g:if>
            <g:if test="${invalidEmail}">
            <p class="well text-danger">
                We don't recognise that email address.
            </p>
            </g:if>

            <g:form action="startPasswordReset" method="POST" onsubmit="submitResetBtn.disabled = true; return true;">
                <label for="email">Your email address</label>
                <input id="email" name="email" type="text" class="form-control" value="${params.email ?: email}"/>
                <br/>

                <img src="${createLink(controller: 'simpleCaptcha', action: 'captcha')}"/>
                <label for="captcha">Type the letters above in the box below:</label>
                <g:textField name="captcha"/>

                <br/>
                <g:submitButton id="submitResetBtn" class="btn btn-primary" name="submit" value="Send Password Reset Link"/>
            </g:form>
        </div>
        <div class="col-md-6">
            <p class="well">
                When you click the Send Password Reset Link button, a one-time link will be emailed to your
                registered email address, allowing you to enter a new password.
                <br/>
                The link will be valid for 48 hours.
            </p>
        </div>

   </div>
</div>
</body>
</html>
