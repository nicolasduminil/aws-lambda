package fr.simplex_software.aws.lambda.functions.pojo;

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
  private String published;
  String publisher;
  String description;
  URL website;
}
