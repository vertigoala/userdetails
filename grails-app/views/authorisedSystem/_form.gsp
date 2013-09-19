<%@ page import="au.org.ala.userdetails.AuthorisedSystem" %>



<div class="fieldcontain ${hasErrors(bean: authorisedSystemInstance, field: 'host', 'error')} ">
	<label for="host">
		<g:message code="authorisedSystem.host.label" default="Host" />
		
	</label>
	<g:textField name="host" value="${authorisedSystemInstance?.host}"/>
</div>

