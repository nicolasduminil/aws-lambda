package fr.simplex_software.aws.lambda.items;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;

import java.util.*;
import java.util.stream.*;

@DynamoDBDocument
public class BankItem
{
  private String id;
  private List<BankAddressItem> bankItemAddresses;
  private String bankName;

  public BankItem()
  {
  }

  public BankItem (Bank bank)
  {
    this.bankName = bank.getBankName();
    this.bankItemAddresses = bank.getBankAddresses().stream().map(BankAddressItem::new).collect(Collectors.toList());
  }

  public List<BankAddressItem> getBankItemAddresses()
  {
    return bankItemAddresses;
  }

  public void setBankItemAddresses(List<BankAddressItem> bankItemAddresses)
  {
    this.bankItemAddresses = bankItemAddresses;
  }

  public String getBankName()
  {
    return bankName;
  }

  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
}
