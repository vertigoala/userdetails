<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Account created</title>
    <asset:stylesheet src="application.css" />
</head>
<body>
<div class="row">
    <h1>
        Please check your email to activate your account and complete the registration process!
    </h1>

    <div class="row">
        <div class="col-md-12">
            <div class="well well-lg">
                Thank you for registering with the ${grailsApplication.config.skin.orgNameLong}.
                <br/>
                To complete your account registration,
                please check your email for an &quot;Activate your account&quot; message<strong> and click the link provided in the email</strong>.
                Note, you will NOT be able to login until you have activated your account via this email.
                <br/>
                If you have any problems please email <a href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>
            </div>
        </div>
   </div>
</div>
</body>
</html>
