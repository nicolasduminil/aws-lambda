package fr.simplex_software.aws.lambda.functions;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result
{
  private ResultType resultType;
  private String message;
  private String description;
}
