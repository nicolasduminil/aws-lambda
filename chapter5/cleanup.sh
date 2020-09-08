#!/bin/bash
if [ -f bucket-name.txt ]
then
  ARTIFACT_BUCKET=$(cat bucket-name.txt)
  XML_BUCKET=$(aws configure list | grep region | awk '{print $2}')-dev-$ARTIFACT_BUCKET
  aws s3 rm --recursive s3://$ARTIFACT_BUCKET
  aws s3 rm --recursive s3://$XML_BUCKET
  aws s3 rb --force s3://$ARTIFACT_BUCKET
  aws s3 rb --force s3://$XML_BUCKET
fi
aws cloudformation delete-stack --stack-name chapter5-stack
aws cloudformation wait stack-delete-complete --stack-name chapter5-stack