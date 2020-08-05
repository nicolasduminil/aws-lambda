#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
aws s3 cp openapi.yaml s3://$BUCKET_NAME/openapi.yaml
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter4-stack --capabilities CAPABILITY_IAM --parameter-overrides BucketName=$BUCKET_NAME
aws cloudformation wait stack-create-complete --stack-name chapter4-stack
API_ID=$(aws apigateway get-rest-apis --query "items[?name=='send-money-api'].id" --output text)
aws apigateway create-deployment --rest-api-id $API_ID --stage-name dev
#DEPLOYMENT_ID=$(aws apigateway get-stage --rest-api-id $API_ID --stage-name dev --query "deploymentId" --output text)
#aws apigateway get-deployment --rest-api-id $API_ID --deployment-id $DEPLOYMENT_ID
