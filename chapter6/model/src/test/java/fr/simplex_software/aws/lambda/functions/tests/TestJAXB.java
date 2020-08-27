package fr.simplex_software.aws.lambda.functions.tests;

import fr.simplex_software.eip.money_transfer.jaxb.*;
import org.junit.jupiter.api.*;

import javax.xml.bind.*;
import java.io.*;
import java.math.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestJAXB
{
  @Test
  public void testUnmarshaller() throws JAXBException
  {
    JAXBContext jaxbContext = JAXBContext.newInstance(MoneyTransfers.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    MoneyTransfers mt = (MoneyTransfers) jaxbUnmarshaller.unmarshal(new File("src/main/resources/xsd/money-xfer.xml"));
    assertNotNull(mt);
    assertTrue (mt.getMoneyTransfers().size() > 0);
    assertEquals (mt.getMoneyTransfers().get(0).getAmount(), new BigDecimal("1000.00"));
  }
}
