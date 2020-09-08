#!/bin/bash
usage ()
{
  echo "Usage : login.sh <CLIENT_ID> <USER_NAME> <PASSWORD>"
  exit
}
if [ "$#" -ne 3 ]
then
  usage
fi
CLIENT_ID=$1
USER_NAME=$2
PASSWORD=$3
aws cognito-idp initiate-auth \
--auth-flow USER_PASSWORD_AUTH \
--auth-parameters "USERNAME=$USER_NAME,PASSWORD=$PASSWORD" \
--client-id $CLIENT_ID
