package fr.simplex_software.aws.lambda.items;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import fr.simplex_software.aws.lambda.converters.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;

import java.math.*;

@DynamoDBTable(tableName = "MoneyTransferOrders")
public class MoneyTransferOrderItem
{
  private String id;
  private String reference;
  private BankAccountItem sourceBankAccountItem;
  private BankAccountItem targetBankAccountItem;
  private BigDecimal amount;

  public MoneyTransferOrderItem() {}

  public MoneyTransferOrderItem (MoneyTransfer moneyTransfer)
  {
    this.amount = moneyTransfer.getAmount();
    this.reference = moneyTransfer.getReference();
    this.sourceBankAccountItem = new BankAccountItem(moneyTransfer.getSourceAccount());
    this.targetBankAccountItem = new BankAccountItem(moneyTransfer.getTargetAccount());
  }

  @DynamoDBHashKey(attributeName = "id")
  @DynamoDBAutoGeneratedKey
  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  @DynamoDBAttribute(attributeName="reference")
  public String getReference()
  {
    return reference;
  }

  public void setReference(String reference)
  {
    this.reference = reference;
  }

  @DynamoDBAttribute(attributeName="sourceBankAccountItem")
  public BankAccountItem getSourceBankAccountItem()
  {
    return sourceBankAccountItem;
  }

  public void setSourceBankAccountItem(BankAccountItem sourceBankAccountItem)
  {
    this.sourceBankAccountItem = sourceBankAccountItem;
  }

  @DynamoDBAttribute(attributeName="targetBankAccountItem")
  public BankAccountItem getTargetBankAccountItem()
  {
    return targetBankAccountItem;
  }

  public void setTargetBankAccountItem(BankAccountItem targetBankAccountItem)
  {
    this.targetBankAccountItem = targetBankAccountItem;
  }

  @DynamoDBAttribute(attributeName="amount")
  public BigDecimal getAmount()
  {
    return amount;
  }

  public void setAmount(BigDecimal amount)
  {
    this.amount = amount;
  }
}
