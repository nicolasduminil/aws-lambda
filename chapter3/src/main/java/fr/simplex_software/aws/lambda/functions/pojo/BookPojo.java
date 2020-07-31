package fr.simplex_software.aws.lambda.functions.pojo;

import com.fasterxml.jackson.databind.annotation.*;
import fr.simplex_software.aws.lambda.functions.serializers.*;
import lombok.*;

import java.net.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPojo
{
  private String isbn;
  private String title;
  private String subtitle;
  private String author;
  @JsonDeserialize(using = ZonedDateDeserializer.class)
  @JsonSerialize(using = ZonedDateSerializer.class)
  private String published;
  String publisher;
  String description;
  URL website;
}
