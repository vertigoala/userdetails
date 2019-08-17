#!/bin/sh
#
# We are testing ngrok as a self-service dynamic https endpoint
# NGROK_URL is ngrok https endpoint (ex: https://c6666564.ngrok.io)
#
export CAS_SERVER=a2a16ce7.ngrok.io
export CAS_DOMAIN=ngrok.io
export ALA_URL=https://$CAS_SERVER # traefik port
sed "s#\$ALA_URL#$ALA_URL#g" ./config/userdetails-config-template.properties > ./config/userdetails-config.properties
