<!doctype html>
<html>
    <head>
        <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
        <meta name="section" content="home"/>
        <g:set var="title">Export Users to CSV</g:set>
        <title>${title} | ${grailsApplication.config.skin.orgNameLong}</title>
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
                <h1>Export Users to CSV</h1>
                <p>
                Choose the options for exporting. The output will be in the following format:
                </p>
                <p>
                    <code>
                    primaryfield1,...,primaryfieldn, ...[,extrafield1,..,extrafieldm][,roles]
                    </code>
                </p>
                <p>
                Where <code>roles</code> is an optional space separated list of the roles that the user should have.
                </p>
                <p>
                    <em>Note:</em> The first row will contain the name of the fields included in the output
                </p>
            </div>
        </div>
        <g:form action="downloadCsvFile" method="post"  class="form-horizontal well well-small">
            <div class="control-group">
            </div>
            <div class="control-group">
                <div class="controls">
                    <label class="checkbox">
                        <g:checkBox name="includeInactiveUsers"/> Include inactive users
                    </label>
                </div>
                <div class="controls">
                    <label class="checkbox">
                        <g:checkBox name="includeExtraFields"/> Include extra data fields (e.g. organisation, primaryUserType, etc)
                    </label>
                </div>
                <div class="controls">
                    <label class="checkbox">
                        <g:checkBox name="includeRoles"/> Include roles
                    </label>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="primaryUsage">
                    Only in selected roles (defaults to all if none selected)
                </label>

                ${roling}
                <div class="controls">
                    <g:select size="10" name="selectedRoles" from="${roles}"  multiple="true"/>
                </div>
            </div>


            <div class="control-group">
                <div class="controls">
                    <button class="btn btn-primary">Download CSV file</button>
                </div>
            </div>
        </g:form>
    </body>
</html>
