<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <title>My profile</title>
</head>
<body>




<div class="row-fluid">
    <h1>Hello ${user.firstName} !</h1>
    <div class="row-fluid">
        <div class="span6">
            <ul>
                <li>
                    <g:link controller="registration" action="editAccount">
                         Edit your profile
                    </g:link>
                </li>
                <li>
                    <a href="http://sightings.ala.org.au/mine">
                         View your sightings
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
                    <a href="http://alerts.ala.org.au/">
                        Manage your alerts
                    </a>
                </li>
            </ul>

        </div>
   </div>
</div>
</body>
</html>
