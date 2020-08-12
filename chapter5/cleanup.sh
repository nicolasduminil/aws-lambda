#!/bin/bash
if [ -f bucket-name.txt ]
then
  ARTIFACT_BUCKET=$(cat bucket-name.txt)
  XML_BUCKET=$(aws configure list | grep region | awk '{print $2}')-dev-$(cat bucket-name.txt)
  aws s3 rb --force s3://$ARTIFACT_BUCKET
  aws s3 rm s3://$XML_BUCKET --recursive
fi
aws cloudformation delete-stack --stack-name chapter5-stack
aws cloudformation wait stack-delete-complete --stack-name chapter5-stack