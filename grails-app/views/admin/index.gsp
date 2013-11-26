<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>User Administration</title>
</head>
<body>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        </ul>
    </div>

    <div class="row-fluid">
        <div class="span12" id="page-body" role="main">
            <h1>User Administration</h1>
            <ul>
                <li><g:link controller="user">Find a user</g:link></li>
                <li><g:link controller="admin" action="resetPasswordForUser">Reset user password</g:link></li>
                <li><g:link controller="role">Roles</g:link></li>
                <li><g:link controller="authorisedSystem">Authorised systems</g:link></li>
                <li><g:link controller="admin" action="bulkUploadUsers">Bulk create user accounts</g:link></li>
            </ul>

            <h2>Web services (HTTP POST)</h2>
            <ul>
                <li>${createLink(controller:'userDetails', action: 'getUserDetails')} - getUserDetails</li>
                <li>${createLink(controller:'userDetails', action: 'getUserList')} - getUserList</li>
                <li>${createLink(controller:'userDetails', action: 'getUserListFull')} - getUserListFull</li>
                <li>${createLink(controller:'userDetails', action: 'getUserListWithIds')} - getUserListWithIds</li>
            </ul>
        </div>
    </div>
</body>
</html>
