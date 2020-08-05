#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
aws s3 cp openapi.yaml s3://$BUCKET_NAME/openapi.yaml
#sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter4-stack --capabilities CAPABILITY_IAM --parameter-overrides BucketName=$BUCKET_NAME
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter4-stack --capabilities CAPABILITY_IAM