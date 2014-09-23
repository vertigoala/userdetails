<%@ page import="au.org.ala.userdetails.User" %>

<div class="row-fluid">

<div class="span6">


<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'firstName', 'error')} ">
    <label for="firstName">
        <g:message code="user.firstName.label" default="First Name"/>

    </label>
    <g:textField name="firstName" value="${userInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastName', 'error')} ">
    <label for="lastName">
        <g:message code="user.lastName.label" default="Last Name"/>

    </label>
    <g:textField name="lastName" value="${userInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'email', 'error')} ">
    <label for="email">
        <g:message code="user.email.label" default="Email"/>

    </label>
    <g:textField name="email" value="${userInstance?.email}"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'tempAuthKey', 'error')} ">--}%
    %{--<label for="tempAuthKey">--}%
        %{--<g:message code="user.tempAuthKey.label" default="Temp Auth Key"/>--}%

    %{--</label>--}%
    %{--<g:textField name="tempAuthKey" value="${userInstance?.tempAuthKey}"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'userName', 'error')} ">--}%
    %{--<label for="userName">--}%
        %{--<g:message code="user.userName.label" default="User Name"/>--}%

    %{--</label>--}%
    %{--<g:textField name="userName" value="${userInstance?.userName}"/>--}%
%{--</div>--}%

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

</div>
<div class="span6 well well-large">



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'activated', 'error')} ">
    <label class="checkbox">
      <g:checkBox name="activated" value="${userInstance?.activated}"/> Activated
    </label>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'locked', 'error')} ">
    <label class="checkbox">
      <g:checkBox name="locked" value="${userInstance?.locked}"/> Locked
    </label>
</div>

<hr/>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'userRoles', 'error')} ">
    <label for="userRoles">
        <g:message code="user.userRoles.label" default=" Roles"/>

    </label>

    <table class="table">
        <g:each in="${userInstance?.userRoles ?}" var="u">
            <tr>
                <td><g:link controller="userRole" action="list" params="[role:u?.encodeAsHTML()]">${u?.encodeAsHTML()}</g:link></td>
                <td><g:link controller="userRole"
                            action="deleteRole"
                            class="btn btn-warning btn-mini"
                            params="[userId:u.user.id,role:u.role.role]"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                        ${message(code: 'default.button.delete.label', default: 'Delete')}
                    </g:link>
                </td>
            </tr>
        </g:each>
    </table>

    <g:link controller="userRole" action="create"
            class="btn"
                    params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'userRole.label', default: 'UserRole')])}</g:link>

</div>

</div>

</div>