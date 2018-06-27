<%@ page contentType="text/html"%>
<html>
  <head><title>${emailTitle}</title></head>
  <body>
    <h3>${emailTitle}</h3>
    <p>
        ${raw(emailBody)}
    </p>
    <g:if test="${password}">
        Your generated password is <strong>${password}</strong>.  To reset your password click the link below:
    </g:if>
    <p>
       <a href="${link}">${link}</a>
    </p>
  </body>
</html>