modules = {
    application {
        resource url:'js/application.js'
    }

    alaSkin {
        resource url: [dir:'less', file:'bootstrap.less', plugin: "ala-web-theme"], attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_alaSkin'
        resource url: '/less/userdetails.less',attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_alaSkin'
    }

    jqueryValidationEngine {
        resource url: 'js/jquery.validationEngine.js'
        resource url: 'js/jquery.validationEngine-en.js'
        resource url: 'css/validationEngine.jquery.css'
    }
}