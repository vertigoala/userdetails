<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>${grailsApplication.config.skin.orgNameShort} accounts</title>
    <asset:stylesheet src="application.css" />
</head>
<body>
<div class="row">
    <div class="col-md-12" id="page-body" role="main">

        <g:if test="${flash.errorMessage || flash.message}">
            <div class="col-md-12">
                <div class="alert alert-danger">
                    <button class="close" onclick="$('.alert').fadeOut();" href="#">×</button>
                    ${flash.errorMessage?:flash.message}
                </div>
            </div>
        </g:if>

        <h1>${grailsApplication.config.skin.orgNameShort} accounts</h1>
        <ul class="userdetails-menu">
            <li><g:link controller="registration" action="createAccount">Create a new account</g:link></li>
            <li><g:link controller="registration" action="forgottenPassword">Reset my password</g:link></li>
            <li><g:link controller="profile">My profile</g:link></li>
        </ul>

    </div>
    <auth:ifAllGranted roles="ROLE_ADMIN">
        <div style="color:white;" class="pull-right">
            <g:link style="color:#DDDDDD; font-weight:bold;" controller="admin">Admin tools (${grailsApplication.config.skin.orgNameShort} administrators only)</g:link>
        </div>
    </auth:ifAllGranted>
</div>
</body>
</html>
