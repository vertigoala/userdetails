### userdetails   [![Build Status](https://travis-ci.org/AtlasOfLivingAustralia/userdetails.svg?branch=master)](https://travis-ci.org/AtlasOfLivingAustralia/userdetails)
userdetails
===========

## About
The Atlas user management app (userdetails) manages profile information for users.

This application is the central repository for user information for Atlas systems requiring authentication.

Userdetails works hand in hand with [CAS] (https://github.com/AtlasOfLivingAustralia/ala-cas-2.0) and both share the same underlying database.

CAS manages the local authentication as well as third party auth provider integrtion.


## General Information

### Technologies
  * Grails framework: 2.3.11
  * JQuery

### Setup
* To do end to end testing, the project requires you to run  [CAS project](https://github.com/AtlasOfLivingAustralia/ala-cas-2.0) on a different port such as `8089`.
* You will need the following local directory:
```
  /data/userdetails/config

```
* Add the external config files for userdetails.
* You will need MySQL running in your local environment
* You will need to run [schema.sql](https://github.com/AtlasOfLivingAustralia/ala-install/blob/master/ansible/roles/userdetails/files/db/schema.sql) in the MySQL to create the database structure


* The app is expected to run in the default port 8080 locally.

