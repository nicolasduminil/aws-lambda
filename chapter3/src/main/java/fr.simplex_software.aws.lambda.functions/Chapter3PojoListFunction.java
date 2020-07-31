package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;
import fr.simplex_software.aws.lambda.functions.pojo.*;
import org.apache.commons.lang3.builder.*;

import java.util.*;

public class Chapter3PojoListFunction implements RequestHandler<List<BookPojo>, Integer>
{
  @Override
  public Integer handleRequest(List<BookPojo> bookPojos, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("### We got a context: " + ReflectionToStringBuilder.toString(context));
    logger.log("### We got a POJO list of " + bookPojos.size() + " POJOs");
    logger.log("### We got a POJO list: " + ReflectionToStringBuilder.toString(bookPojos.get(0)));
    return context.getRemainingTimeInMillis();
  }
}
