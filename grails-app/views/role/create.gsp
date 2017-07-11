<%@ page import="au.org.ala.userdetails.Role" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="${grailsApplication.config.skin.layout}">
		<g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <r:require module="jqueryValidationEngine"/>
        <r:script>
            $(function(){
                $('#validation-container').validationEngine('attach', {scroll: false});
                $("#saveRoleForm").submit(function() {
                   var valid = $('#validation-container').validationEngine('validate');
                   if(!valid) {
                     event.preventDefault();
                   }
                });
            });
        </r:script>
	</head>
	<body>
		<a href="#create-role" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-role" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
                <div class="message well warning text-danger" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${roleInstance}">
              <div class="well warning">
                <ul class="errors" role="alert">
                    <g:eachError bean="${roleInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
               </div>
			</g:hasErrors>

            <div id="validation-container" class="validation-container">
			<g:form action="save" id="saveRoleForm">
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="btn btn-primary save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
            </div>
		</div>
	</body>
</html>
