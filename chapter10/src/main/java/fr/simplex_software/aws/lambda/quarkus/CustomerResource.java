package fr.simplex_software.aws.lambda.quarkus;

import lombok.extern.slf4j.*;
import org.eclipse.microprofile.faulttolerance.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.concurrent.atomic.*;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class CustomerResource
{
  @Inject
  private CustomerService customerService;
  private AtomicLong counter = new AtomicLong(0);
  private Float failRatio = 0.5f;

  @GET
  @Retry(maxRetries = 4, retryOn = RuntimeException.class)
  public List<Customer> getCustomers()
  {
    final Long invocationNumber = counter.getAndIncrement();
    if (new Random().nextFloat() < failRatio)
    {
      log.error("### CustomerResource.getCustomers() invocation {} failed", invocationNumber);
      throw new RuntimeException("Resource failure.");
    }
    log.info(">>> CustomerResource.getCustomers() invocation {} returning successfully", invocationNumber);
    return customerService.getCustomers();
  }

  @Path("/{id}")
  @GET
  public Response getCustomerById(@PathParam("id") int id)
  {
    final Long invocationNumber = counter.getAndIncrement();
    try
    {
      Customer customer = customerService.getCustomerById(id);
      log.info("### CustomerResource.getCustomerById() invocation {} returning successfully", invocationNumber);
      return Response.ok(customer).build();
    }
    catch (RuntimeException e)
    {
      String message = e.getClass().getSimpleName() + ": " + e.getMessage();
      log.error("### CustomerResource.getCustomerById() invocation {} failed", invocationNumber);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
    }
  }

  @Path("/customer/{name}")
  @GET
  @Timeout(250)
  @Fallback(fallbackMethod = "getCustomerByLastNameFallback")
  public Response getCustomersByLastName(@PathParam("name") String lastName)
  {
    long started = System.currentTimeMillis();
    final long invocationNumber = counter.getAndIncrement();
    try
    {
      Thread.sleep(new Random().nextInt(500));
      log.info(">>> CustomerService.getCustomersByLastName() invocation {} returning successfully", invocationNumber);
      return Response.ok(customerService.getCustomersByLastName(lastName)).build();
    }
    catch (InterruptedException e)
    {
      log.error("### CustomerService.getCustomersByLastName() invocation {} timed out after %d{} ms",
        invocationNumber, System.currentTimeMillis() - started);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  public AtomicLong getCounter()
  {
    return counter;
  }

  public void setCounter(AtomicLong counter)
  {
    this.counter = counter;
  }

  public void resetCounter()
  {
    this.counter.set(0);
  }

  public Float getFailRatio()
  {
    return failRatio;
  }

  public void setFailRatio(Float failRatio)
  {
    this.failRatio = failRatio;
  }

  private Response getCustomerByLastNameFallback(String name)
  {
    log.info("### Falling back to CustomerResource.getCustomerByLastNameFallback()");
    return Response.ok(Collections.singletonList(customerService.getCustomersByLastName(name))).build();
  }
}
