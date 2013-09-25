<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>My profile</title>
</head>
<body>

<div class="inner row-fluid">
    <div id="breadcrumb" class="span12">
        <ol class="breadcrumb">
            <li><a href="${grailsApplication.config.homeUrl}">Home</a> <span class=" icon icon-arrow-right"></span></li>
            <li class="active">My profile</li>
        </ol>
    </div>
</div>

<div class="row-fluid">
    <h1>Hello ${user.firstName} !</h1>
    <div class="row-fluid">
        <div class="span6">
            <ul>
                <li>
                    <g:link controller="registration" action="editAccount">
                         Update your profile
                    </g:link>
                </li>
                <li>
                    <a href="http://sightings.ala.org.au/mine">
                         View sightings recorded through the Atlas
                    </a>
                </li>
                <li>
                    <a href="http://spatial.ala.org.au/actions/dashboard">
                        Tabulate and graph all functions you've used in the Spatial Portal (species, areas, layers, tools, imports and exports).
                    </a>
                </li>
                <li>
                    <a href="http://volunteer.ala.org.au/user/myStats">
                        View your tasks on the Biodiversity Volunteer Portal.
                    </a>
                </li>
                <li>
                    <a href="http://lists.ala.org.au/speciesList/list">
                        View your uploaded species lists.
                    </a>
                </li>
                <li>
                    <a href="http://biocache.ala.org.au/occurrences/search?q=*%3A*&fq=assertion_user_id%3A%22${user.id}%22">
                        View records you have annotated.
                    </a>
                </li>

                <li>
                    <a href="http://alerts.ala.org.au/">
                        Manage your alerts
                    </a>
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
                    <strong>You have connect to flickr account with username:
                        <a href="http://www.flickr.com/photos/${props.flickrId}">${props.flickrUsername}</a>.
                    </strong>
                    <p>
                    Linking with Flick enables images shared through Flickr to be linked to your Atlas account
                    so they can be attributed to you.
                    </p>
                    <g:link controller="profile" class="btn" action="removeFlickrLink" target="_blank">Remove link to flickr account</g:link>.
                </g:if>
                <g:else>
                    <p>
                    Linking with Flick enables images shared through Flickr to be linked to your Atlas account
                    so they can be attributed to you.
                    </p>

                    <span class="btn">
                        <oauth:connect provider="flickr">Link to my Flickr account</oauth:connect>
                    </span>
                </g:else>
            </div>
        </div>
   </div>
</div>
</body>
</html>
