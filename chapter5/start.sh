#!/bin/bash
aws s3 cp src/main/resources/xsd/money-xfer.xml s3://$(aws configure list | grep region | awk '{print $2}')-dev-$(cat bucket-name.txt)/money-xfer.yaml
