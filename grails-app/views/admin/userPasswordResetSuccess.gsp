<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Please check your email | ${grailsApplication.config.skin.orgNameLong} </title>
</head>
<body>
<div class="row-fluid">
    <h1>User password reset successfully</h1>
    <div class="row-fluid">
        <div class="span12">
            <p class="well">
                The password has been reset for <b>${email}</b> and an email has been sent to the user containing the new password.
            </p>
        </div>
   </div>
</div>
</body>
</html>
