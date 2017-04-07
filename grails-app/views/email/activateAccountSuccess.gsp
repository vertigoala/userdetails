<%@ page contentType="text/html"%>
<html>
<head><title>Your account has been successfully activated</title></head>
<body>
<h1>Your account has been successfully activated</h1>
<p>
    You are now automatically subscribed to the following alerts:

    ${activatedAlerts}
</p>
<p>
    Please visit <a href="${grailsApplication.config.alerts.url}/notification/myAlerts">My Alerts</a> to modify your alert configurations.
</p>
</body>
</html>