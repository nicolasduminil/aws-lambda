#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
STAGE_NAME=dev
AWS_REGION=$(aws configure list | grep region | awk '{print $2}')
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
aws s3 cp openapi.yaml s3://$BUCKET_NAME/openapi.yaml
sam deploy --template-file ../chapter6/template.yaml --s3-bucket $BUCKET_NAME --stack-name chapter6-stack --capabilities CAPABILITY_IAM --parameter-overrides BucketName=$BUCKET_NAME
API_ID=$(aws apigateway get-rest-apis --query "items[?name=='send-money-api'].id" --output text)
aws apigateway create-deployment --rest-api-id $API_ID --stage-name $STAGE_NAME >/dev/null 2>&1
echo "api-url=https://$API_ID.execute-api.$AWS_REGION.amazonaws.com/$STAGE_NAME" > ../chapter5/src/main/resources/aws-lambda.properties
sam deploy --template-file ../chapter5/template.yaml --s3-bucket $BUCKET_NAME --stack-name chapter5-stack --capabilities CAPABILITY_IAM --parameter-overrides BucketName=$AWS_REGION-$STAGE_NAME-$BUCKET_NAME
aws cloudformation wait stack-create-complete --stack-name chapter5-stack
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter7-stack --capabilities CAPABILITY_IAM
aws cloudformation wait stack-create-complete --stack-name chapter7-stack
../chapter5/start.sh

