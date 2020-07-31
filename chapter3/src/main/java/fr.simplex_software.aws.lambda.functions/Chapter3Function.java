package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;
import org.apache.commons.lang3.builder.*;

import java.util.*;

public class Chapter3Function implements RequestHandler<Map<String,String>, String>
{
  @Override
  public String handleRequest(Map<String, String> event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("### We got a context: " + ReflectionToStringBuilder.toString(context));
    logger.log("### We got an event: " + event.toString());
    logger.log("### We got an event of type: " + event.getClass().toString());
    return new String("200 OK");
  }
}
