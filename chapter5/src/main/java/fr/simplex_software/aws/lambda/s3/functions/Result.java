package fr.simplex_software.aws.lambda.s3.functions;

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
