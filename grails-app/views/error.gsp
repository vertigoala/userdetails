<!doctype html>
<html>
	<head>
		<title>Grails Runtime Exception</title>
		<meta name="layout" content="${grailsApplication.config.skin.layout}">
		<asset:stylesheet src="application.css" />
	</head>
	<body>
		<p>
		<g:renderException exception="${exception}" />
		</p>
	</body>
</html>