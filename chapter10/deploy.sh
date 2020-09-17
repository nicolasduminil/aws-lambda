#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter10-stack --capabilities CAPABILITY_IAM
aws cloudformation wait stack-create-complete --stack-name chapter10-stack


