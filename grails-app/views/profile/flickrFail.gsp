<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>My profile | ${grailsApplication.config.skin.orgNameLong}</title>
</head>
<body>
<div class="row-fluid">
    <h1>Flickr account link failed</h1>
    <p class="well text-error">
        We were unable to link to your Flickr account.
        If this problem persists, please email <a href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>
    </p>
</div>
</body>
</html>
