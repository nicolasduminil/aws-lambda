package fr.simplex_software.aws.lambda.functions.tests;

import fr.simplex_software.aws.lambda.common.*;
import fr.simplex_software.eip.money_transfer.jaxb.*;
import org.apache.commons.io.*;
import org.junit.jupiter.api.*;

import javax.xml.bind.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestLambda
{
  @Test
  public void testMarshaller() throws Exception
  {
    JAXBContext jaxbContext = JAXBContext.newInstance(MoneyTransfers.class);
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    MoneyTransfers moneyTransfers = MoneyTransferUtil.getMoneyTransfers();
    File out = new File("src/main/resources/xsd/money-xfer2.xml");
    jaxbMarshaller.marshal(moneyTransfers, out);
    assertTrue(FileUtils.contentEqualsIgnoreEOL (out, new File("src/main/resources/xsd/money-xfer3.xml"), null));
  }
}
