package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;

import javax.json.bind.*;
import java.util.*;

public class Chapter3Function implements RequestHandler<Map<String,String>, String>
{
  @Override
  public String handleRequest(Map<String, String> event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    Jsonb jsonb = JsonbBuilder.create();
    logger.log("CONTEXT: " + jsonb.toJson(context));
    logger.log("EVENT: " + jsonb.toJson(event));
    logger.log("EVENT TYPE: " + event.getClass().toString());
    return new String("200 OK");
  }
}
