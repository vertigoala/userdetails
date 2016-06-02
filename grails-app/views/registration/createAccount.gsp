<!doctype html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}"/>
    <meta name="section" content="home"/>
    <r:require modules="jqueryValidationEngine, autocomplete"/>
    <g:if test="${!alreadyRegistered && edit}">
        <g:set var="title">Edit your account</g:set>
    </g:if>
    <g:else>
        <g:set var="title">Create your account</g:set>
    </g:else>
    <title>${title}</title>
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
    <g:if test="${inactiveUser}">
        <div class="row-fluid warning well">
            <p class="text-error">A user is already registered with the email address <b>${params.email}</b> however it is currently disabled.
            </p>

            <p>
                If you think this is an error or you disabled your account please contact <a
                    href="mailto:${grailsApplication.config.supportEmail}">${grailsApplication.config.supportEmail}</a>.
            </p>
        </div>
    </g:if>
    <g:elseif test="${alreadyRegistered}">
        <div class="row-fluid warning well">
            <p class="text-error">A user is already registered with the email address <b>${params.email}</b>.</p>

            <p>
                To login with this user name, <a
                    href="${grailsApplication.config.security.cas.loginUrl}">click here</a>.<br/>
                To start the process of resetting your password, <g:link controller="registration"
                                                                         action="forgottenPassword"
                                                                         params="${[email: params.email]}">click here</g:link>.
            </p>
        </div>
    </g:elseif>

    <div class="row-fluid">
        <div class="span4">
            <div>
            <g:form name="updateAccountForm" method="POST" action="${edit ? 'update' : 'register'}" controller="registration"  onsubmit="updateAccountSubmit.disabled = true; return true;">

                    <label for="firstName">First name</label>
                    <input id="firstName" name="firstName" type="text" class="input-xlarge" value="${user?.firstName}" data-validation-engine="validate[required]"/>

                    <label for="lastName">Last name</label>
                    <input id="lastName" name="lastName" type="text" class="input-xlarge" value="${user?.lastName}"  data-validation-engine="validate[required]"/>


                    <label for="email">Email address</label>
                    <input id="email" name="email" type="text" class="input-xlarge" value="${user?.email}"
                           data-validation-engine="validate[required,custom[email]]"
                           data-errormessage-value-missing="Email is required!"
                    />

                    <g:if test="${!edit}">
                    <label for="password">Password</label>
                    <input id="password"
                           name="password"
                           class="input-xlarge"
                           value=""
                           data-validation-engine="validate[required, minSize[8]]"
                           data-errormessage-value-missing="Password is required!"
                           type="password"
                    />

                    <label for="reenteredPassword">Reentered password</label>
                    <input id="reenteredPassword"
                           name="reenteredPassword"
                           class="input-xlarge"
                           value=""
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
                    <input id="primaryUserType" name="primaryUserType" type="text" class="input-xlarge usageAuto"
                           value="${props?.primaryUserType?:''}"
                           data-validation-engine="validate[required]"
                    />

                    <label for="secondaryUserType">Secondary usage</label>
                    <input id="secondaryUserType" name="secondaryUserType" type="text"  class="input-xlarge usageAuto"
                           value="${props?.secondaryUserType?:''}"
                           data-validation-engine="validate[required]"
                    />
                <br/>
                <g:if test="${edit}">
                    <button id="updateAccountSubmit" class="btn btn-ala">Update account</button>
                    <button id="disableAccountSubmit" class="btn btn-ala delete">Disable account</button>
                </g:if>
                <g:else>
                    <button id="updateAccountSubmit" class="btn btn-ala">Create account</button>
                </g:else>
            </g:form>
            </div>
        </div>
        <div class="span8 well">
            <g:if test="${!edit}">
                <p>
                    To create your new account, fill in the fields opposite and click "Create".
                </p>
            </g:if>
            <p>
                In the Primary and Secondary Usage fields you can enter your own text to describe your
                intended usage of the site. Examples include: "Amateur naturalist", "Photographer", "Ecologist".
            </p>
            <p>
                For the Atlas' policy on the collection and use of personal information see our
                <a href="${grailsApplication.config.privacyPolicy}">Privacy Policy</a>.
            </p>
            <p>
                Your email address will be your ALA account id.  An account activation link will be
                emailed to the address provided.
            </p>
        </div>
   </div>
</div>
</body>
<r:script>
    $(function(){

        //$('.typeahead').typeahead();
        var usageOptions = [
            "Amateur naturalist","Amateur photographer","Biodiversity Research","Biogeographer",
            "Biologist","Botanist","Bush Regenerator","BushCare leader","Citizen scientist","Collection manager",
            "Collection technician","Communications","Conservation Planner","Consultant","Data manager",
            "Database Manager","Eco Tourism","Ecologist","Education","Education programs developer","Entomologist",
            "Environmental Officer","Environmental Scientist","Farming","Field Researcher","Forester","Geochemist",
            "GIS visualisation","Identification","IT specialist","Land manager","Land owner","Librarian","Mycologist",
            "Naturalist","Observer","Park Ranger","Pest control","Pest Identification","PhD Student","Policy developer",
            "Predicting distribution","Researcher","Science communicator","Scientific Illustrator","Scientist",
            "Student","Taxonomist","Teacher","Veterinary Pathologist","Volunteer","Volunteer Digitizer","Writer",
            "Zoologist"
        ];

        $(".usageAuto").autocomplete(usageOptions, {});
        $('#updateAccountForm').validationEngine('attach', { scroll: false });
        $("#updateAccountSubmit").click(function(e) {

            $("#updateAccountSubmit").attr('disabled','disabled');

            var pm = $('#password').val() == $('#reenteredPassword').val();
            if(!pm){
                alert("The supplied passwords do not match!");
            }

            var valid = $('#updateAccountForm').validationEngine('validate');

            if (valid && pm) {
                $("form[name='updateAccountForm']").submit();
            } else {
                $('#updateAccountSubmit').removeAttr('disabled');
                e.preventDefault();
            }
        });

        $("#disableAccountSubmit").click(function(e) {

            $("#disableAccountSubmit").attr('disabled','disabled');

            var valid = confirm("${message(code: 'default.button.delete.user.confirm.message', default: "Are you sure want to disable your account? You won't be able to login again. You will have to contact support@ala.org.au in the future if you want to reactivate your account.")}");

            if (valid) {
                $('#updateAccountForm').validationEngine('detach');
                $("form[name='updateAccountForm']").attr('action','disableAccount');
                $("form[name='updateAccountForm']").submit();
            } else {
                $('#disableAccountSubmit').removeAttr('disabled');
                e.preventDefault();
            }
        });



    });
</r:script>
</html>
