package fr.simplex_software.aws.lambda.quarkus;

import lombok.extern.slf4j.*;

import javax.enterprise.context.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

@ApplicationScoped
@Slf4j
public class CustomerService
{
  private Map<Integer, Customer> customers = new ConcurrentHashMap<>();

  public CustomerService()
  {
    customers = Map.of(1, new Customer(1, "Robert", "Smith", "8 Villa du Danube", "Paris", "75019", "France"),
      2, new Customer(2, "Jane", "Hopkyns", "10 rue des Petites Ecuries", "Paris", "75010", "France"),
      3, new Customer(3, "Fabienne", "Fontaine", "15 rue Lecourbe", "Paris", "75015", "France"),
      4, new Customer(4, "Brigitte", "Larivière", "8 rue du Fbg. de St. Honoré", "Paris", "7508", "France"));
  }

  public List<Customer> getCustomers()
  {
    return new ArrayList<>(customers.values());
  }

  public Customer getCustomerById(Integer id)
  {
    return customers.get(id);
  }

  public List<Customer> getCustomersByLastName (String lastName)
  {
    return customers.values().stream().filter(c -> c.getLastName().equals(lastName)).limit(2).collect(Collectors.toList());
  }
}
