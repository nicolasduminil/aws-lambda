package fr.simplex_software.aws.lambda.common;

import fr.simplex_software.eip.money_transfer.jaxb.*;

import java.math.*;

public class MoneyTransferUtil
{
  public static MoneyTransfers getMoneyTransfers()
  {
    MoneyTransfers moneyTransfers = new MoneyTransfers();
    moneyTransfers.getMoneyTransfers().add(getMoneyTransfer());
    return moneyTransfers;
  }

  public static MoneyTransfer getMoneyTransfer()
  {
    MoneyTransfer moneyTransfer = new MoneyTransfer();
    moneyTransfer.setAmount(new BigDecimal("1000.00"));
    moneyTransfer.setReference("refernce123");
    moneyTransfer.setSourceAccount(getBankAccount());
    moneyTransfer.setTargetAccount(getBankAccount());
    return moneyTransfer;
  }

  public static BankAccount getBankAccount()
  {
    BankAccount bankAccount = new BankAccount();
    bankAccount.setAccountID("source-bank-account-id");
    bankAccount.setAccountNumber("source-bank-account-number");
    bankAccount.setAccountType(BankAccountType.CHECKING);
    bankAccount.setBank(getBank());
    bankAccount.setSortCode("source-bank-account-sort-code");
    bankAccount.setTransCode("source-bank-account-trans-code");
    return bankAccount;
  }

  public static Bank getBank()
  {
    Bank bank = new Bank();
    bank.setBankName("source-account-bank-name");
    bank.getBankAddresses().add(getBankAddress());
    return bank;
  }

  public static BankAddress getBankAddress()
  {
    BankAddress bankAddress = new BankAddress();
    bankAddress.setCityName("source-account-bank-city-name");
    bankAddress.setCountryName("source-account-bank-country-name");
    bankAddress.setPoBox("source-account-bank-po-box");
    bankAddress.setStreetName("source-account-bank-street-name");
    bankAddress.setZipCode("source-account-bank-zip-code");
    return bankAddress;
  }
}
