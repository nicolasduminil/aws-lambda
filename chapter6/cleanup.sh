aws iam detach-role-policy --role-name create-order-role --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
aws iam detach-role-policy --role-name create-order-role --policy-arn arn:aws:iam::aws:policy/AmazonSQSFullAccess
aws iam delete-role --role-name create-order-role
if [ -f bucket-name.txt ]
then
  aws s3 rm --recursive s3://$(cat bucket-name.txt)
  aws s3 rb --force s3://$(cat bucket-name.txt)
fi
aws cloudformation delete-stack --stack-name chapter6-stack
aws cloudformation wait stack-delete-complete --stack-name chapter6-stack
