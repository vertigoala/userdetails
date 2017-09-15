<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Please check your email</title>
    <asset:stylesheet src="application.css" />
</head>
<body>
<div class="row">
    <h1>Password reset successfully</h1>
    <div class="row">
        <div class="col-md-12">
            <p class="well">
                Your password has been reset. To login click the button below.
            </p>
            <a href="${grailsApplication.config.security.cas.loginUrl}?service=${java.net.URLEncoder.encode(serverUrl, 'UTF-8')}" class="btn btn-primary">Login</a>
        </div>
   </div>
</div>
</body>
</html>
