package fr.simplex_software.aws.lambda.items;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;

@DynamoDBDocument
public class BankAddressItem
{
  private String id;
  private String streetName;
  private String streetNumber;
  private String poBox;
  private String cityName;
  private String zipCode;
  private String countryName;

  public BankAddressItem() {}

  public BankAddressItem (BankAddress bankAddress)
  {
    this.cityName = bankAddress.getCityName();
    this.countryName = bankAddress.getCountryName();
    this.poBox = bankAddress.getPoBox();
    this.streetName = bankAddress.getStreetName();
    this.streetNumber = bankAddress.getStreetNumber();
    this.zipCode = bankAddress.getZipCode();
  }

  public String getStreetName()
  {
    return streetName;
  }

  public void setStreetName(String streetName)
  {
    this.streetName = streetName;
  }

  public String getStreetNumber()
  {
    return streetNumber;
  }

  public void setStreetNumber(String streetNumber)
  {
    this.streetNumber = streetNumber;
  }

  public String getPoBox()
  {
    return poBox;
  }

  public void setPoBox(String poBox)
  {
    this.poBox = poBox;
  }

  public String getCityName()
  {
    return cityName;
  }

  public void setCityName(String cityName)
  {
    this.cityName = cityName;
  }

  public String getZipCode()
  {
    return zipCode;
  }

  public void setZipCode(String zipCode)
  {
    this.zipCode = zipCode;
  }

  public String getCountryName()
  {
    return countryName;
  }

  public void setCountryName(String countryName)
  {
    this.countryName = countryName;
  }
}
