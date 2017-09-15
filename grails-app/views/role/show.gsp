
<%@ page import="au.org.ala.userdetails.Role" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="${grailsApplication.config.skin.layout}">
		<g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<meta name="breadcrumbParent" content="${g.createLink(action:"list")},${g.message(code:"default.list.label", args:[entityName])}" />
		<asset:stylesheet src="application.css" />
	</head>
	<body>
		<div class="row">
			<div class="col-sm-12">
				<div id="show-role" class="content scaffold-show" role="main">
					<h1><g:message code="default.show.label" args="[entityName]" /></h1>
					<g:if test="${flash.message}">
						<div class="message" role="status">${flash.message}</div>
					</g:if>
					<ol class="property-list role">

						<g:if test="${roleInstance?.role}">
							<li class="fieldcontain">
								<span id="role-label" class="property-label"><g:message code="role.role.label" default="Role" /></span>

								<span class="property-value" aria-labelledby="role-label"><g:fieldValue bean="${roleInstance}" field="role"/></span>

							</li>
						</g:if>

						<g:if test="${roleInstance?.description}">
							<li class="fieldcontain">
								<span id="description-label" class="property-label"><g:message code="role.description.label" default="Description" /></span>

								<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${roleInstance}" field="description"/></span>

							</li>
						</g:if>

					</ol>
					<g:form>
						<fieldset class="buttons">
							<g:hiddenField name="id" value="${roleInstance?.id}" />
							<g:link class="edit" action="edit" id="${roleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="btn btn-danger delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
