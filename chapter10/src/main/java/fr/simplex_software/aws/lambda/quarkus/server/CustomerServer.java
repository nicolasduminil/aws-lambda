package fr.simplex_software.aws.lambda.quarkus.server;

import fr.simplex_software.aws.lambda.quarkus.*;

import javax.ws.rs.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

@Path("/server")
@Produces("application/json")
public class CustomerServer
{
  private Map<Integer, Customer> customers = new ConcurrentHashMap<>();

  public CustomerServer()
  {
    customers = Map.of(1, new Customer(1, "Robert", "Smith", "8 Villa du Danube", "Paris", "75019", "France"),
      2, new Customer(2, "Jane", "Hopkyns", "10 rue des Petites Ecuries", "Paris", "75010", "France"),
      3, new Customer(3, "Fabienne", "Fontaine", "15 rue Lecourbe", "Paris", "75015", "France"),
      4, new Customer(4, "Brigitte", "Larivière", "8 rue du Fbg. de St. Honoré", "Paris", "7508", "France"));
  }

  @GET
  public List<Customer> getCustomers()
  {
    return new ArrayList<>(customers.values());
  }

  @GET
  @Path("/{id}")
  public Customer getCustomerById(@PathParam("id") Integer id)
  {
    return customers.get(id);
  }

  @GET
  @Path("/customer/{name}")
  public List<Customer> getCustomerByLastName(@PathParam("name") String name)
  {
    return customers.values().stream().filter(c -> c.getLastName().equals(name)).limit(2).collect(Collectors.toList());
  }
}
