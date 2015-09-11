<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>Oops - there was a problem</title>
</head>
<body>
<div class="row-fluid">
    <h1>Oops - there was a problem!</h1>
    <div class="row-fluid">
        <p>
            There was problem updating your account.
            Please contact <a href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>
        </p>
   </div>
</div>
</body>
</html>
