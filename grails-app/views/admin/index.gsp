<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>User Administration | ${grailsApplication.config.skin.orgNameLong}</title>
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
                <li><g:link controller="admin" action="exportUsers">Export users to CSV file</g:link></li>
                <li><g:link controller="alaAdmin" action="index">ALA admin page</g:link></li>
            </ul>

            <h2>Web services (HTTP POST)</h2>
            <ul>
                <li>${createLink(controller:'userDetails', action: 'getUserDetails')}?userName=&lt;email_or_userid&gt;[&amp;includeProps=true] - return a JSON object containing id, email and display name for a given user, use includeProps=true to get additional information such as organisation</li>
                <li>${createLink(controller:'userDetails', action: 'getUserList')} - return a JSON map of user email address to display name</li>
                <li>${createLink(controller:'userDetails', action: 'getUserListWithIds')} - return a JSON map of user ids to display name</li>
                <li>${createLink(controller:'userDetails', action: 'getUserListFull')} - return all users as a JSON array</li>
                <li>${createLink(controller:'userDetails', action: 'getUserDetailsFromIdList')} - return the details for a list of user ids.  POST a JSON body like this:
                    <pre>{
  "userIds": ["1","2"], // list of numeric user ids to retrieve
  "includeProps": true // optional, set to true to include organisation, primary usage, etc
}</pre>
                    This will return a JSON doc like this:
                    <pre>{
  "users": {
    "1":{
      "userId":"1",
      "userName":"user@email.address",
      "firstName":"User Given Name",
      "lastName":"User Surname",
      "email":"user@email.address",
      "props":{
        "secondaryUserType":"Citizen scientist",
        "organisation":"User Organisation",
        "telephone":"555-123456",
        "city":"User City",
        "state":"User State",
        "primaryUserType":"IT specialist"
      }
    }
  },
  "invalidIds":[2],
  "success":true
}</pre>
                </li>
            </ul>
            <h2>Web services (HTTP GET)</h2>
            <ul>
                <li><a href="${createLink(uri:'/ws/getUserStats')}">${createLink(uri:'/ws/getUserStats')}</a> - a public web service that returns a JSON object containing a 'description' of the service, 'totalUsers' and 'totalUsersOneYearAgo' counts.</li>
            </ul>
        </div>
    </div>
</body>
</html>
