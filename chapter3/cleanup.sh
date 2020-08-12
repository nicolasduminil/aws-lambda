aws cloudformation delete-stack --stack-name chapter3-stack
if [ -f bucket-name.txt ]
then
  ARTIFACT_BUCKET=$(cat bucket-name.txt)
  aws s3 rb --force s3://$ARTIFACT_BUCKET
fi
aws logs delete-log-group --log-group-name /aws/lambda/Chapter3Function
aws logs delete-log-group --log-group-name /aws/lambda/Chapter3StreamFunction
aws logs delete-log-group --log-group-name /aws/lambda/Chapter3PojoFunction
aws logs delete-log-group --log-group-name /aws/lambda/Chapter3PojoListFunction