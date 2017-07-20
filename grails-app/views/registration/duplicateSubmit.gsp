<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Reset password form submission</title>
</head>
<body>
<div class="row">
    <h1>Results of resetting your password</h1>
    <div class="row">
        <div class="alert alert-warning">
            The system has detected more than one password reset attempt.  This can happen if the "Set my password" button is clicked more than once on the page you entered your new password.
        </div>

        <strong>Next steps:</strong>
        <ul class="userdetails-menu">
            <li>
                It is likely that your password has been reset successfully, please try and login <a href="${grailsApplication.config.security.cas.loginUrl}?service=${java.net.URLEncoder.encode(serverUrl, 'UTF-8')}">here</a> using your new password.
            </li>
            <li>
                If your new password doesn't work, please start the process again <g:link controller="registration" action="forgottenPassword">here</g:link>.
            </li>
            <li>
                If problems persist, please contact <a href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>
            </li>
        </ul>

   </div>
</div>
</body>
</html>
