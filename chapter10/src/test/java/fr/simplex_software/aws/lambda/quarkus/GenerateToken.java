package fr.simplex_software.aws.lambda.quarkus;

import io.smallrye.jwt.build.*;
import lombok.extern.slf4j.*;
import org.eclipse.microprofile.jwt.*;

import java.util.*;

@Slf4j
public class GenerateToken
{
  public static void main(String[] args)
  {
    String token = Jwt.issuer("CN=nduminil, O=SimplexSoftware, C=FR").upn("nduminil").groups(new HashSet<>(Arrays.asList("User", "Admin"))).sign();
    log.info (">>> GenerateToken.main(): Token is {}", token);
  }
}
