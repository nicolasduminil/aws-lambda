if [ -f bucket-name.txt ]
then
  aws s3 rm --recursive s3://$(cat bucket-name.txt)
  aws s3 rb --force s3://$(cat bucket-name.txt)
fi
aws cloudformation delete-stack --stack-name chapter9-stack
aws cloudformation wait stack-delete-complete --stack-name chapter9-stack
