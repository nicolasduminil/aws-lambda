package fr.simplex_software.aws.lambda.functions.serializers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

public class ZonedDateDeserializer extends StdDeserializer<LocalDate>
{
  protected ZonedDateDeserializer()
  {
    super(LocalDate.class);
  }

  @Override
  public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException
  {
    return LocalDate.parse(jsonParser.readValueAs(String.class), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }
}
