#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
STAGE_NAME=dev
AWS_REGION=$(aws configure list | grep region | awk '{print $2}')
aws s3 mb s3://$BUCKET_NAME
echo $BUCKET_NAME > bucket-name.txt
aws s3 cp openapi.yaml s3://$BUCKET_NAME/openapi.yaml
ROLE_ARN=$(aws iam create-role --role-name create-order-role \
  --assume-role-policy-document '{"Version": "2012-10-17","Statement": [{ "Effect": "Allow", "Principal": {"Service": "lambda.amazonaws.com"}, "Action": "sts:AssumeRole"}]}' \
  --query 'Role.Arn' --output text)
aws iam attach-role-policy --role-name create-order-role --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
aws iam attach-role-policy --role-name create-order-role --policy-arn arn:aws:iam::aws:policy/AmazonSQSFullAccess
sam deploy --s3-bucket $BUCKET_NAME --stack-name chapter6-stack --capabilities CAPABILITY_IAM \
  --parameter-overrides ParameterKey=BucketName,ParameterValue=$BUCKET_NAME ParameterKey=RoleArn,ParameterValue=$ROLE_ARN \
  ParameterKey=Email,ParameterValue=nicolas.duminil@wanadoo.fr
API_ID=$(aws apigateway get-rest-apis --query "items[?name=='send-money-api'].id" --output text)
aws apigateway create-deployment --rest-api-id $API_ID --stage-name $STAGE_NAME >/dev/null 2>&1
echo "api-url=https://$API_ID.execute-api.$AWS_REGION.amazonaws.com/$STAGE_NAME/orders" > ../chapter5/src/main/resources/aws-lambda.properties

