<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>Please check your email</title>
</head>
<body>
<div class="row-fluid">
    <h1>Password reset successfully</h1>
    <div class="row-fluid">
        <div class="span12">
            <p class="well">
                Your password has been reset. To login click the button below.
            </p>
            <a href="${grailsApplication.config.security.cas.loginUrl}?service=${java.net.URLEncoder.encode(serverUrl, 'UTF-8')}" class="btn bt-ala">Login</a>
        </div>
   </div>
</div>
</body>
</html>
