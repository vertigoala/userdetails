<%@ page import="au.org.ala.userdetails.Role" %>



<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'role', 'error')} ">
	<label for="role">
		<g:message code="role.role.label" default="Role" />
		
	</label>
	<g:textField name="role" value="${roleInstance?.role}" data-validation-engine="validate[required]"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="role.description.label" default="Description" data-validation-engine="validate[required]" />
		
	</label>
	<g:textField name="description"  class="input-xxlarge" value="${roleInstance?.description}"/>
</div>

