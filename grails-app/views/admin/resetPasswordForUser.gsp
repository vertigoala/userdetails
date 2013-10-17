<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>Reset my password</title>
</head>
<body>
<div class="row-fluid">
    <h1>Reset password for user</h1>

    <g:if test="${emailNotRecognised}">
    <div class="row-fluid warning well">
        <p class="text-error">Email address <b>${email}</b> not recognised.</p>
    </div>
    </g:if>

    <div class="row-fluid">
        <div class="span6">
            <g:form action="sendPasswordResetEmail" method="POST">
                <label for="email">Email address of user</label>
                <input id="email" name="email" type="text" class="input-xlarge" value="${params.email ?: email}"/>
                <br/>
                <g:submitButton class="btn btn-ala" name="submit" value="Send user password"/>
            </g:form>
        </div>
        <div class="span6">
            <p class="well">
                When you click the Send Password Reset Link button, a one-time link will be emailed to your
                registered email address, allowing you to enter a new password.
                <br/>
                The link will be valid for 48 hours.
            </p>
        </div>
   </div>
</div>
</body>
</html>
