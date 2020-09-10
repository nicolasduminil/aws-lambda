#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter9-stack --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides ParameterKey=Version,ParameterValue=dev
aws cloudformation wait stack-create-complete --stack-name chapter9-stack
echo api-url=$(aws cloudformation describe-stacks --stack-name chapter9-stack --query 'Stacks[0].Outputs[?OutputKey==`ApiUrl`].OutputValue' --output text) > src/test/resources/aws-lambda.properties
