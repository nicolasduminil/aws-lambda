package fr.simplex_software.aws.lambda.functions.serializers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

public class ZonedDateSerializer extends StdSerializer<LocalDate>
{
  public ZonedDateSerializer()
  {
    super(LocalDate.class);
  }

  @Override
  public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
  {
    jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
  }
}
