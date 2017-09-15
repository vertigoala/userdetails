<%@ page contentType="text/html"%>
<html>
  <head><title>${emailTitle}</title></head>
  <body>
    <h1>${emailTitle}</h1>
    <p>
        ${emailBody}
    </p>
    <g:if test="${password}">
        Your generated password is <strong>${password}</strong>. If you would like to change it, click the link below.
    </g:if>
    <p>
       <a href="${link}">${link}</a>
    </p>
  </body>
</html>