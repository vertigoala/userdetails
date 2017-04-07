<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Account created | ${grailsApplication.config.skin.orgNameLong}</title>
</head>
<body>
<div class="row-fluid">
    <h1>
        Please check your email to activate your account!
    </h1>

    <div class="row-fluid">
        <div class="span12 well well-large">
            Thank you for registering with the ${grailsApplication.config.skin.orgNameLong}. To complete your account registration,
            please check your email and <b>click the link provided in the email</b>.
            <br/>
            If you have any problems please email
            <a href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>.
        </div>
   </div>
</div>
</body>
</html>
