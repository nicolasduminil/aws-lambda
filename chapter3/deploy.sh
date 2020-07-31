#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter3-stack --capabilities CAPABILITY_IAM
aws lambda invoke --function-name Chapter3Function --payload file://event.json --cli-binary-format raw-in-base64-out out1.txt
aws lambda invoke --function-name Chapter3StreamFunction --payload file://event.json --cli-binary-format raw-in-base64-out out.json
aws lambda invoke --function-name Chapter3PojoFunction --payload file://event.json --cli-binary-format raw-in-base64-out out2.txt
aws lambda invoke --function-name Chapter3PojoListFunction --payload file://event.json --cli-binary-format raw-in-base64-out out3.txt