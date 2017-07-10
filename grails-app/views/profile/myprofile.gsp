<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <title>My profile | ${grailsApplication.config.skin.orgNameLong}</title>
</head>
<body>

<div class="inner row">
    <div id="breadcrumb" class="col-md-12">
        <ol class="breadcrumb">
            <li><a href="${grailsApplication.config.homeUrl}">Home</a> <span class="glyphicon glyphicon-arrow-right"></span></li>
            <li class="active">My profile</li>
        </ol>
    </div>
</div>

<div class="row">
    <h1>Hello ${user.firstName} !</h1>
    <div class="row">
        <div class="col-md-6">
            <ul>
                <li>
                    <g:link controller="registration" action="editAccount">
                         Update your profile
                    </g:link>
                </li>
                <li>
                    <a href="${grailsApplication.config.sightings.url}">
                         View your timeline of sightings recorded through the Atlas
                    </a>
                </li>
                <li>
                    <a href="${grailsApplication.config.spatial.url}">
                        Tabulate and graph all functions you've used in the Spatial Portal
                    </a>
                </li>
                <li>
                    <a href="${grailsApplication.config.volunteer.url}">
                        View your tasks on the DigiVol Portal
                    </a>
                </li>
                <li>
                    <a href="${grailsApplication.config.lists.url}">
                        View your uploaded species lists
                    </a>
                </li>
                <li>
                    <a href="${grailsApplication.config.biocache.search.url}?q=*%3A*&fq=assertion_user_id%3A%22${user.id}%22">
                        View records you have annotated
                    </a>
                </li>
                <li>
                    <a href="${grailsApplication.config.alerts.url}">
                        Manage your alerts
                    </a>
                </li>
                <li>
                    <g:link controller="registration" action="forgottenPassword">
                        Reset my password
                    </g:link>
                </li>

                <g:if test="${isAdmin}">
                <li>
                    <g:link controller="admin">
                        Admin tools
                    </g:link>
                </li>
                </g:if>
            </ul>

            <h3>External site linkages</h3>
            <div class="well well-small">
                <h4>Flickr</h4>
                <g:if test="${props.flickrUsername}">
                    <strong>You have connected to flickr account with username:
                        <a href="http://www.flickr.com/photos/${props.flickrId}">${props.flickrUsername}</a>.
                    </strong>
                    <p>
                    Linking with Flickr enables images shared through

                    <a href="http://www.flickr.com/groups/encyclopedia_of_life/">Flickr EOL Group</a>

                    to be linked to your Atlas account so they can be attributed to you.
                    </p>



                    <g:link controller="profile" class="btn btn-default" action="removeFlickrLink" target="_blank">Remove link to flickr account</g:link>
                </g:if>
                <g:else>
                    <p>
                    Linking with Flick enables images shared through Flickr to be linked to your Atlas account
                    so they can be attributed to you.
                    </p>

                    <span class="btn btn-default">
                        <oauth:connect provider="flickr">Link to my Flickr account</oauth:connect>
                    </span>
                </g:else>
            </div>
        </div>
   </div>
</div>
</body>
</html>
