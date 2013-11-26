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

        %{--<div class="inner row-fluid">--}%
            %{--<div id="breadcrumb" class="span12">--}%
                %{--<ol class="breadcrumb">--}%
                    %{--<li><a href="${grailsApplication.config.homeUrl}">Home</a> <span class=" icon icon-arrow-right"></span></li>--}%
                    %{--<li><a href="${createLink(controller:'admin', action:'index')}">User Details Administration</a> <span class=" icon icon-arrow-right"></span></li>--}%
                    %{--<li class="active">${title}</li>--}%
                %{--</ol>--}%
            %{--</div>--}%
        %{--</div>--}%

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
                    <em>Note:</em> If an email address in the file already exists in the database then that user will not be created.
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

            <div class="control-group">
                <div class="controls">
                    <button class="btn btn-primary">Upload</button>
                </div>
            </div>
        </g:form>
    </body>
</html>
