package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;
import fr.simplex_software.aws.lambda.functions.pojo.*;
import org.apache.commons.lang3.builder.*;

public class Chapter3PojoFunction implements RequestHandler<BookPojo, BookPojo>
{
  @Override
  public BookPojo handleRequest(BookPojo bookPojo, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("### We got a context: " + ReflectionToStringBuilder.toString(context));
    logger.log("### We got a POJO: " + bookPojo.toString());
    logger.log("### We got a POJO of type: " + bookPojo.getClass().toString());
    return bookPojo;
  }
}
