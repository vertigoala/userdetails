<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Account created | ${grailsApplication.config.skin.orgNameLong}</title>
    <asset:stylesheet src="application.css" />
</head>
<body>
<div class="row">
    <h1>
        Congratulations! Your account has been activated successfully.
    </h1>

    <div class="row">
        <div class="col-md-12">
            <div class="well well-lg">
                Please login in order to access <a href="${grailsApplication.config.security.cas.loginUrl}?email=${user.email}&service=${grailsApplication.config.redirectAfterFirstLogin}">My Profile</a>.
            </div>
        </div>
    </div>
</div>
</body>
</html>