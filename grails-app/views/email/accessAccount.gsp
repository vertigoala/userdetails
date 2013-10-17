<%@ page contentType="text/html"%>
<html>
  <head><title>Accessing your account</title></head>
  <body>
    <h1>Accessing your account</h1>
    <p>
        Below is a generated password you can use to log into your account.
        It is recommended that you reset your password.
    </p>
    <p>
      ${generatedPassword}
    </p>
    <p>
       To login, <a href="${link}">click here</a>.
    </p>
  </body>
</html>