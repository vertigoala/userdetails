<!doctype html>
<html>
    <head>
        <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
        <meta name="section" content="home"/>
        <g:set var="title">Export Users to CSV</g:set>
        <title>${title} | ${grailsApplication.config.skin.orgNameLong}</title>
    </head>
    <body>
        <g:if test="${flash.message}">
            <div class="alert alert-danger">
                ${flash.message}
            </div>
        </g:if>

        <div class="row">
            <div class="col-md-12" id="page-body" role="main">
                <h1>Export Users to CSV</h1>
                <p>
                Choose the options for exporting. The output will be in the following format:
                </p>
                <p>
                    <code>
                    primaryfield1,...,primaryfieldn[,extrafield1,..,extrafieldm][,roles]
                    </code>
                </p>
                <p>
                    Where primary fields are <code>${primaryFields}</code>
                </p>
                <p>
                    Extra fields are <code>${extraFields}</code>
                </p>
                <p>
                    And <code>roles</code> is an optional space separated list of the user roles.
                </p>

                <p>
                    <em>Note:</em> The first row will contain the name of the fields included in the output
                </p>
            </div>
        </div>
        <g:form name="exportUsersForm" action="downloadUsersCsvFile" method="post"  class="form-horizontal well well-small">
            <div class="form-group">
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <g:checkBox name="includeInactiveUsers"/> Include inactive users
                        </label>
                    </div>
                </div>
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <g:checkBox name="includeExtraFields"/> Include extra data fields (e.g. organisation, primaryUserType, etc)
                        </label>
                    </div>
                </div>
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <g:checkBox name="includeRoles"/> Include roles field
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2" for="selectedRoles">
                    Only users in selected roles (defaults to all if none selected)
                </label>
                <div class="col-sm-10">
                    <g:select class="form-control" size="10" name="selectedRoles" from="${roles}"  multiple="true"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="downloadFileButton" class="btn btn-primary">Download CSV file</button>
                </div>
            </div>
        </g:form>
    </body>
    <asset:script type="text/javascript">
        $(function(){
            $("#downloadFileButton").click(function(e) {
                $("#downloadFileButton").attr('disabled','disabled');
                var valid = confirm("${message(code: 'default.button.admin.export.users.confirm.message', default: "Your download will start shortly after clicking accept/OK. You will need to refresh the page if you want to download a file with different parameters.")}");
                if (valid) {
                    $("form[name='exportUsersForm']").submit();
                } else {
                    $('#downloadFileButton').removeAttr('disabled');
                    e.preventDefault();
                }
            });
        });
    </asset:script>
</html>
