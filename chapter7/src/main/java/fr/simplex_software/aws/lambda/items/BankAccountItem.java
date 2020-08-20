package fr.simplex_software.aws.lambda.items;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import fr.simplex_software.aws.lambda.converters.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;

@DynamoDBDocument
public class BankAccountItem
{
  private String id;
  private BankItem bankItem;
  private String bankAccountId;
  private BankAccountType accountType;
  private String sortCode;
  private String accountNumber;
  private String transCode;

  public BankAccountItem()
  {
  }

  public BankAccountItem (BankAccount bankAccount)
  {
    this.accountNumber = bankAccount.getAccountNumber();
    this.accountType = bankAccount.getAccountType();
    this.bankAccountId = bankAccount.getAccountID();
    this.bankItem = new BankItem (bankAccount.getBank());
    this.sortCode = bankAccount.getSortCode();
    this.transCode = bankAccount.getTransCode();
  }

  public BankItem getBankItem()
  {
    return bankItem;
  }

  public void setBankItem(BankItem bankItem)
  {
    this.bankItem = bankItem;
  }

  public String getBankAccountId()
  {
    return bankAccountId;
  }

  public void setBankAccountId(String bankAccountId)
  {
    this.bankAccountId = bankAccountId;
  }

  @DynamoDBTypeConverted(converter= BankAccountTypeConverter.class)
  public BankAccountType getAccountType()
  {
    return accountType;
  }

  public void setAccountType(BankAccountType accountType)
  {
    this.accountType = accountType;
  }

  public String getSortCode()
  {
    return sortCode;
  }

  public void setSortCode(String sortCode)
  {
    this.sortCode = sortCode;
  }

  public String getAccountNumber()
  {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber)
  {
    this.accountNumber = accountNumber;
  }

  public String getTransCode()
  {
    return transCode;
  }

  public void setTransCode(String transCode)
  {
    this.transCode = transCode;
  }
}
