<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="section" content="home"/>
    <r:require module="jqueryValidationEngine"/>
    <g:if test="${!alreadyRegistered && edit}">
        <g:set var="title">Edit your account</g:set>
    </g:if>
    <g:else>
        <g:set var="title">Create your account</g:set>
    </g:else>
    <title>${title}</title>
    <r:script>
        $(function(){
            $('.typeahead').typeahead();
            $('#validation-container').validationEngine('attach', {scroll: false});
            $("#updateAccountForm").submit(function() {
                alert('submitting...');
               console.log("Submitting....")
               var valid = $('#validation-container').validationEngine('validate');
               if(!valid) {
                 event.preventDefault();
               }
            });
        });
    </r:script>
</head>
<body>

<div class="inner row-fluid">
    <div id="breadcrumb" class="span12">
        <ol class="breadcrumb">
            <li><a href="${grailsApplication.config.homeUrl}">Home</a> <span class=" icon icon-arrow-right"></span></li>
            <g:if test="${edit}">
                <li><g:link controller="profile">My profile</g:link> <span class=" icon icon-arrow-right"></span></li>
            </g:if>
            <li class="active">${title}</li>
        </ol>
    </div>
</div>

<div class="row-fluid">
    <h1>${title}</h1>
    <g:if test="${alreadyRegistered}">
    <div class="row-fluid warning well">
        <p class="text-error">A user is already registered with the email address <b>${params.email}</b>. </p>
        <p>
            To login with this user name, <a href="${grailsApplication.config.security.cas.loginUrl}">click here</a>.<br/>
            To start the process of resetting your password, <g:link controller="registration" action="forgottenPassword" params="${[email:params.email]}">click here</g:link>.
        </p>
    </div>
    </g:if>

    <div class="row-fluid">
        <div class="span4">
            <div class="validationEngineContainer" id="validation-container">
            <g:form id="updateAccountForm" method="POST" action="${edit ? 'update' : 'register'}" controller="registration" >

                    <label for="firstName">First name</label>
                    <input id="firstName" name="firstName" type="text" class="input-xlarge" value="${user?.firstName}" data-validation-engine="validate[required]"/>

                    <label for="lastName">Last name</label>
                    <input id="lastName" name="lastName" type="text" class="input-xlarge" value="${user?.lastName}"  data-validation-engine="validate[required]"/>

                    <g:if test="${!edit}">
                    <label for="email">Email address</label>
                    <input id="email" name="email" type="text" class="input-xlarge" value="${user?.email}"
                           data-validation-engine="validate[required,custom[email]]"
                           data-errormessage-value-missing="Email is required!"
                    />
                    </g:if>

                    <g:if test="${!edit}">
                    <label for="password">Password</label>
                    <input id="password" name="password" class="input-xlarge" value=""
                           data-validation-engine="validate[required, minSize[8]]"
                           data-errormessage-value-missing="Password is required!"
                           type="password"
                    />
                    </g:if>

                    <label for="organisation">Organisation</label>
                    <input id="organisation" name="organisation" type="text" class="input-xlarge" value="${props?.organisation}"/>

                    <label for="city">City</label>
                    <input id="city" name="city" type="text" class="input-xlarge" value="${props?.city}" />

                    <label for="state">State/territory</label>
                    <g:select id="state" name="state"
                              value="${props?.state}"
                              keys="['N/A', 'ACT', 'NSW', 'WA', 'VIC', 'SA', 'TAS', 'NT', 'QLD']"
                              from="['N/A', 'Australian Capital Territory', 'New South Wales',
                                      'Western Australia', 'Victoria', 'South Australia', 'Tasmania',
                                      'Northern Territory', 'Queensland']"
                    />

                    <label for="telephone">Telephone</label>
                    <input id="telephone" name="telephone" type="text" class="input-xlarge" value="${props?.telephone}" />

                    <label for="primaryUserType">Primary usage</label>
                    <input id="primaryUserType" name="primaryUserType" type="text" class="input-xlarge"
                           value="${props?.primaryUserType}"
                           data-provide="typeahead"
                           data-items="20"
                           data-source='["Amateur naturalist","Amateur photographer","Biodiversity Research","Biogeographer","Biologist","Botanist","Bush Regenerator","BushCare leader","Citizen scientist","Collection manager","Collection technician","Communications","Conservation Planner","Consultant","Data manager","Database Manager","Eco Tourism","Ecologist","Education","Education programs developer","Entomologist","Environmental Officer","Environmental Scientist","Farming","Field Researcher","Forester","Geochemist","GIS visualisation","Identification","IT specialist","Land manager","Land owner","Librarian","Mycologist ","Naturalist","Observer","Park Ranger","Pest control","Pest Identification","PhD Student","Policy developer","Predicting distribution","Researcher","Science communicator","Scientific Illustrator","Scientist","Student","Taxonomist","Teacher","Veterinary Pathologist","Volunteer","Volunteer Digitizer","Writer","Zoologist"]'
                           data-validation-engine="validate[required]"
                    />

                    <label for="secondaryUserType">Secondary usage</label>
                    <input id="secondaryUserType" name="secondaryUserType" type="text" class="input-xlarge"
                           value="${props?.secondaryUserType}"
                           data-provide="typeahead" data-items="20"
                           data-source='["Amateur naturalist","Amateur photographer","Biodiversity Research","Biogeographer","Biologist","Botanist","Bush Regenerator","BushCare leader","Citizen scientist","Collection manager","Collection technician","Communications","Conservation Planner","Consultant","Data manager","Database Manager","Eco Tourism","Ecologist","Education","Education programs developer","Entomologist","Environmental Officer","Environmental Scientist","Farming","Field Researcher","Forester","Geochemist","GIS visualisation","Identification","IT specialist","Land manager","Land owner","Librarian","Mycologist ","Naturalist","Observer","Park Ranger","Pest control","Pest Identification","PhD Student","Policy developer","Predicting distribution","Researcher","Science communicator","Scientific Illustrator","Scientist","Student","Taxonomist","Teacher","Veterinary Pathologist","Volunteer","Volunteer Digitizer","Writer","Zoologist"]'
                           data-validation-engine="validate[required]"
                    />
                <br/>
                <g:if test="${edit}">
                    <g:submitButton class="btn btn-ala" name="submit" value="Update account"  />
                </g:if>
                <g:else>
                    <g:submitButton class="btn btn-ala" name="submit" value="Create account" />
                </g:else>
            </g:form>
            </div>
        </div>
        <div class="span8 well">
            <p>
                To create your new account, fill in the fields opposite and click "Create".
            </p>
            <p>
                In the Primary and Secondary Usage fields you can enter your own text or
                select a value from the drop-down list.
            </p>
            <p>
                For the Atlas' policy on the collection and use of personal information see our
                <a href="http://www.ala.org.au/about/terms-of-use/privacy-policy/">Privacy Policy</a>.
            </p>
            <p>
                Your email address will be your ALA account id.  An account activation link will be
                emailed to the address provided.
                If you do not wish your actual name to be displayed with your occurrence records or
                annotations, please enter an alias.
            </p>
        </div>
   </div>
</div>
</body>
</html>
