<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Reset password link expired</title>
</head>
<body>
<div class="row-fluid">
    <h1>Reset password link expired</h1>
    <div class="row-fluid">
        <p>
            This link to start the process to reset your password has expired.<br/>
            If you have completed the <g:link controller="registration" action="forgottenPassword">Reset your password</g:link>
            form recently, please check your email for a new email with a link that will take your through to page where
            you can provide a new password.

            <br/>
            Alternatively you can start the process again <g:link controller="registration" action="forgottenPassword">here</g:link>.

            <br/>
            If problems persist, please contact <a href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>
        </p>
   </div>
</div>
</body>
</html>
