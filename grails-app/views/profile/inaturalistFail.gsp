<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>My profile | ${grailsApplication.config.skin.orgNameLong}</title>
    <asset:stylesheet src="application.css" />
</head>
<body>
<div class="row">
    <h1>iNaturalist account link failed</h1>
    <p class="well text-danger">
        We were unable to link to your iNaturalist account.
        If this problem persists, please email <a href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>
    </p>
</div>
</body>
</html>
