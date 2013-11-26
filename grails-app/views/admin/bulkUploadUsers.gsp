<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <meta name="section" content="home"/>
        <g:set var="title">Bulk Load Users</g:set>
        <title>${title}</title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><a href="${createLink(controller:'admin', action:'index')}"><i class="icon-wrench"></i>&nbsp;Administration</a></li>
            </ul>
        </div>

        <g:if test="${flash.message}">
            <div class="alert alert-danger">
                ${flash.message}
            </div>
        </g:if>

        <div class="row-fluid">
            <div class="span12" id="page-body" role="main">
                <h1>Bulk Load Users</h1>
                <p>
                Choose a CSV file to load. The file should be in the following format:
                </p>
                <p>
                    <code>
                    email_address,first_name,surname,roles
                    </code>
                </p>
                <p>
                Where <code>roles</code> is an optional space separated list of the roles that the user should have.
                </p>
                <p>
                    <em>Note:</em> If an email address in the file already exists in the database then that user will not be created, but any missing roles will be added to the existing account.
                </p>
            </div>
        </div>
        <g:form action="loadUsersCSV" method="post" enctype="multipart/form-data" class="form-horizontal well well-small">
            <div class="control-group">
                <div class="controls">
                    <input type="file" name="userList" />
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <label class="checkbox">
                        <g:checkBox name="firstRowHasFieldNames"/> First row contains field names
                    </label>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="primaryUsage">
                    Primary usage (will default to 'Not Supplied')
                </label>

                <div class="controls">
                    <g:textField name="primaryUsage" />
                </div>
            </div>

            <h4>Password Reset Email</h4>
            <div class="control-group">
            An email will be sent out to each user created, prompting them to reset their password.
            You can customize the content of this email by filling out the fields below, or you can leave them blank to use the default wording.
            </div>

            <div class="control-group">
                <label class="control-label" for="emailSubject">
                    Subject
                </label>
                <div class="controls">
                    <g:textField name="emailSubject" class="input-xlarge" />
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="emailTitle">
                    Title
                </label>
                <div class="controls">
                    <g:textField name="emailTitle" class="input-xlarge"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="emailBody">
                    Body
                </label>
                <div class="controls">
                    <g:textArea name="emailBody" class="input-xxlarge" rows="5"/>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <button class="btn btn-primary">Upload</button>
                </div>
            </div>
        </g:form>
    </body>
</html>
