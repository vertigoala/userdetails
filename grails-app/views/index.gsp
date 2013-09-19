<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>User management</title>
</head>
<body>
%{--<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>--}%
<div class="row-fluid">
    <div class="span12" id="page-body" role="main">
        <h1>ALA accounts</h1>
        <ul>
            <li><g:link controller="registration" action="createAccount">Create a new account</g:link></li>
            <li><g:link controller="registration" action="forgottenPassword">Reset my password</g:link></li>
            <li><g:link controller="profile">My profile</g:link></li>
        </ul>
    </div>
    <div style="color:white;" class="pull-right">
        <g:link style="color:#DDDDDD; font-weight:bold;" controller="admin">Admin tools (Atlas administrators only)</g:link>
    </div>
</div>
</body>
</html>
