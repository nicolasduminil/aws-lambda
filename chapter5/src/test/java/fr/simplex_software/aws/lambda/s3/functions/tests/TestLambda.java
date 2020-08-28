package fr.simplex_software.aws.lambda.s3.functions.tests;

import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import fr.simplex_software.aws.lambda.s3.functions.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import javax.json.bind.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestLambda
{
  private static Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

  @Test
  public void test() throws Exception
  {
    AmazonS3 mockAmazonS3 = Mockito.mock(AmazonS3.class);
    S3Event s3Event = fixtureS3Event();
    log.info("### TestLambda.test(): s3Event {}", s3Event.getRecords().size());
    String key = s3Event.getRecords().get(0).getS3().getObject().getKey();
    String bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
    Mockito.when(mockAmazonS3.getObject(bucketName, key)).thenReturn(fixtureS3ReturnValue(key));
    FilePoller filePoller = new FilePoller(mockAmazonS3);
    assertTrue(filePoller.handleRequest(s3Event, Mockito.mock(Context.class)).getResultType().equals(ResultType.SUCCESS));
  }

  private S3Event fixtureS3Event() throws Exception
  {
    return jsonb.fromJson(getClass().getResourceAsStream("/s3-event.json"), S3Event.class);
  }

  private S3Object fixtureS3ReturnValue(String key)
  {
    S3Object s3Object = new S3Object();
    s3Object.setObjectContent(getClass().getResourceAsStream(String.format("/%s", key)));
    return s3Object;
  }
}
