<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <meta name="breadcrumb" content="My Profile" />
    <title>My profile | ${grailsApplication.config.skin.orgNameLong}</title>
    <asset:stylesheet src="application.css" />
</head>
<body>

<div class="row">
    <div class="col-md-6">
        <h1>Hello ${user.firstName} !</h1>
        <ul class="userdetails-menu">
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
                <a href="${grailsApplication.config.biocache.search.url}%22${user.id}%22">
                    View records you have annotated
                </a>
            </li>
            <li>
                <a href="${grailsApplication.config.biocache.myDownloads.url}">
                    View your downloaded records
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
        <g:if test="${grailsApplication.config.oauth.providers.flickr.enabled}">
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



                <g:link controller="profile" class="btn btn-default" action="removeLink" params="[provider: 'flickr']">Remove link to flickr account</g:link>
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
        </g:if>
        <g:if test="${grailsApplication.config.oauth.providers.inaturalist.enabled}">
        <div class="well well-small">
            <h4>iNaturalist</h4>
            <g:if test="${props.inaturalistId}">
                <strong>You have connected to iNaturalist with username:
                    <a href="http://www.inaturalist.org/people/${props.inaturalistId}">${props.inaturalistUsername}</a>
                </strong>
                <p>
                    Linking with iNaturalist enables sharing your sightings with the ALA
                </p>

                <g:link controller="profile" class="btn btn-default" action="removeLink" params="[provider: 'inaturalist']">Remove link to iNaturalist account</g:link>
            </g:if>
            <g:else>
                <p>
                    Linking with iNaturalist enables sightings made through iNaturalist to be linked to your Atlas account
                    so they can be attributed to you.
                </p>

                <span class="btn btn-default">
                    <oauth:connect provider="inaturalist">Link to my iNaturalist account</oauth:connect>
                </span>
            </g:else>
        </div>
        </g:if>
    </div>
</div>
</body>
</html>
