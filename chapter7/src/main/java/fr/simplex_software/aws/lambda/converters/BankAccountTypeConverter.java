package fr.simplex_software.aws.lambda.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import fr.simplex_software.aws.lambda.items.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;

public class BankAccountTypeConverter implements DynamoDBTypeConverter<String, BankAccountType>
{
  @Override
  public String convert(BankAccountType bankAccountType)
  {
    return bankAccountType.name();
  }

  @Override
  public BankAccountType unconvert(String s)
  {
    return BankAccountType.fromValue(s);
  }
}
