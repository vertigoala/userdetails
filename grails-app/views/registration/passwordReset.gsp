<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>Please supply your new password</title>
</head>
<body>
<div class="row-fluid">
    <h1>Please supply your new password</h1>

    <g:if test="${passwordMatchFail}">
        <div class="well">
        <p class="text-error">
            The supplied passwords did not match.
        </p>
        </div>
    </g:if>

    <div class="row-fluid">

        <g:form controller="registration" action="updatePassword">

            <label for="password">Your new password</label>
            <input id="password" type="password" name="password" value=""/>

            <label for="reenteredPassword">Re-enter Password</label>
            <input id="reenteredPassword" type="password" name="reenteredPassword" value=""/>

            <input id="authKey" type="hidden" name="authKey" value="${authKey}"/>
            <input id="userId" type="hidden" name="userId" value="${user.id}"/>

            <br/>
            <br/>

            <g:submitButton name="submit" value="Set password" class="btn btn-ala"/>
        </g:form>
   </div>
</div>
</body>
</html>
