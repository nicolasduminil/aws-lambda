package fr.simplex_software.aws.lambda.quarkus;

import lombok.extern.slf4j.*;

import javax.annotation.security.*;
import javax.enterprise.context.*;
import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/customers")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class CustomerResource
{
  @Inject
  private CustomerService customerService;

  @GET
  @PermitAll
  public List<Customer> getCustomers()
  {
    return customerService.getCustomers();
  }

  @Path("/{id}")
  @GET
  @RolesAllowed({ "User", "Admin" })
  public Response getCustomerById(@PathParam("id") int id)
  {
    return Response.ok(customerService.getCustomerById(id)).build();
  }

  @Path("/customer/{name}")
  @GET
  @RolesAllowed({ "Admin" })
  public Response getCustomersByLastName(@PathParam("name") String lastName)
  {
    return Response.ok(customerService.getCustomersByLastName(lastName)).build();
  }
}
