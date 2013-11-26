<%@ page import="au.org.ala.userdetails.AuthorisedSystem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'authorisedSystem.label', default: 'AuthorisedSystem')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-authorisedSystem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><a href="${createLink(controller:'admin', action:'index')}"><i class="icon-wrench"></i>&nbsp;Administration</a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-authorisedSystem" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
            <div class="row-fluid">
                <div class="span6">
                    <table>
                    <thead>
                        <tr>

                            <g:sortableColumn property="host" title="${message(code: 'authorisedSystem.host.label', default: 'Host')}" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${authorisedSystemInstanceList}" status="i" var="authorisedSystemInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                            <td><g:link action="show" id="${authorisedSystemInstance.id}">${fieldValue(bean: authorisedSystemInstance, field: "host")}</g:link></td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
                    <div class="pagination">
                        <g:paginate total="${authorisedSystemInstanceTotal}" />
                    </div>
                </div>
                <div class="span6 well">
                    This is a list of IP address that can access the web services providing user information.
                    Requests from IP addresses no listed here will get a HTTP 403 Forbidden response.
                </div>
            </div>
		</div>
	</body>
</html>
