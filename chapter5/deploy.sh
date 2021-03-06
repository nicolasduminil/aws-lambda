#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
STAGE_NAME=dev
AWS_REGION=$(aws configure list | grep region | awk '{print $2}')
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter5-stack --capabilities CAPABILITY_IAM --parameter-overrides BucketName=$AWS_REGION-$STAGE_NAME-$BUCKET_NAME
aws cloudformation wait stack-create-complete --stack-name chapter5-stack
