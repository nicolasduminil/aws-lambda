#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
aws s3 mb s3://$BUCKET_NAME
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter3-stack --capabilities CAPABILITY_IAM
aws lambda invoke --function-name AwsLambdaTestFunction --payload file://event.json --cli-binary-format raw-in-base64-out out.json