aws cloudformation delete-stack --stack-name chapter7-stack
if [ -f bucket-name.txt ]
then
  ARTIFACT_BUCKET=$(cat bucket-name.txt)
  aws s3 rb --force s3://$ARTIFACT_BUCKET
fi
aws cloudformation wait stack-delete-complete --stack-name chapter7-stack
