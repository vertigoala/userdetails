<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>Please supply your new password</title>
</head>
<body>
<r:script>
    $(function(){
        $("#submitResetBtn").click(function() {
            $('#submitResetBtn').attr('disabled','disabled');
            if($('#reenteredPassword').val() != $('#password').val()){
                alert("The supplied passwords do not match!")
                $('#submitResetBtn').removeAttr('disabled');
                event.preventDefault();
            } else {
                //submit the form
                $("form[name='resetPasswordForm']").submit();
            }
        });
    });
</r:script>

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

        <g:form name="resetPasswordForm" controller="registration" action="updatePassword">

            <label for="password">Your new password</label>
            <input id="password" type="password" name="password" value=""/>

            <label for="reenteredPassword">Re-enter Password</label>
            <input id="reenteredPassword" type="password" name="reenteredPassword" value=""/>

            <input id="authKey" type="hidden" name="authKey" value="${authKey}"/>
            <input id="userId" type="hidden" name="userId" value="${user.id}"/>

            <br/>
            <br/>
            <button id="submitResetBtn" class="btn btn-ala">Set my password</button>
        </g:form>
   </div>
</div>
</body>
</html>
