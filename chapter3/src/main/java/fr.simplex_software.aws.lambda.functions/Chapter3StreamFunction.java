package fr.simplex_software.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.*;

import javax.json.bind.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class Chapter3StreamFunction implements RequestStreamHandler
{
  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
  {
    LambdaLogger logger = context.getLogger();
    Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
    try (
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII));
      PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.US_ASCII)))
    )
    {
      HashMap<?,?> event = jsonb.fromJson(reader, HashMap.class);
      logger.log("### We got a stream of type: " + inputStream.getClass().toString());
      logger.log("### We got an event of type: " + event.getClass().toString());
      writer.write(jsonb.toJson(event));
      if (writer.checkError())
        logger.log("WARNING: Writer encountered an error.");
    }
    catch (IllegalStateException exception)
    {
      logger.log(exception.toString());
    }
  }
}
