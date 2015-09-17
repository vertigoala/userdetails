<%@ page contentType="text/html"%>
<html>
  <head><title>Activate your account</title></head>
  <body>
    <h1>Activate your account</h1>
    <p>
        Please click the link below to activate your ${grailsApplication.config.skin.orgNameShort} account.
        This will take you to a form where you can provide a new
        password for your account.
    </p>
    <p>
       <a href="${link}">${link}</a>
    </p>
  </body>
</html>