#!/bin/bash
usage ()
{
  echo "Usage : login-first.sh <USER_POOL_ID> <CLIENT_ID> <USER_NAME> <PASSWORD>"
  exit
}
if [ "$#" -ne 4 ]
then
  usage
fi
USER_POOL_ID=$1
CLIENT_ID=$2
USER_NAME=$3
PASSWORD=$4
AUTH_CHALLENGE_SESSION=`aws cognito-idp initiate-auth \
--auth-flow USER_PASSWORD_AUTH \
--auth-parameters "USERNAME=$USER_NAME,PASSWORD=$PASSWORD" \
--client-id $CLIENT_ID \
--query "Session" \
--output text`
AUTH_TOKEN=`aws cognito-idp admin-respond-to-auth-challenge \
--user-pool-id $USER_POOL_ID \
--client-id $CLIENT_ID \
--challenge-responses "NEW_PASSWORD=Testing1,USERNAME=$USER_NAME" \
--challenge-name NEW_PASSWORD_REQUIRED \
--session $AUTH_CHALLENGE_SESSION \
--query "AuthenticationResult.IdToken" \
--output text`
echo "token=$AUTH_TOKEN" > src/test/resources/aws-lambda-token.properties
echo "Your password has been changed to Testing1. Your JWT is $AUTH_TOKEN"
