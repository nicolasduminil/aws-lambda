aws cloudformation delete-stack --stack-name chapter7-stack
aws cloudformation wait stack-delete-complete --stack-name chapter7-stack
aws cloudformation delete-stack --stack-name chapter6-stack
aws cloudformation wait stack-delete-complete --stack-name chapter6-stack
aws cloudformation delete-stack --stack-name chapter5-stack
aws cloudformation wait stack-delete-complete --stack-name chapter5-stack
if [ -f bucket-name.txt ]
then
  ARTIFACT_BUCKET=$(cat bucket-name.txt)
  aws s3 rb --force s3://$ARTIFACT_BUCKET
fi
aws cloudformation wait stack-delete-complete --stack-name chapter7-stack