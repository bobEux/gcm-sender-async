package org.whispersystems.gcm.server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.whispersystems.gcm.server.util.JsonHelpers.jsonFixture;

public class MessageTest {

  @Test
  public void testMinimal() throws IOException {
    Message message = Message.newBuilder()
                            .withDestination("1")
                            .withNotificationPart("title", "title")
                            .withNotificationPart("body", "body")
                            .build();

    assertEquals(message.serialize(), jsonFixture("fixtures/message-minimal.json"));
  }

  @Test
  public void testComplete() throws IOException {
    Message message = Message.newBuilder()
                            .withDestination("1")
                            .withNotificationPart("title", "title")
                            .withNotificationPart("body", "body")
                            .build();

    assertEquals(message.serialize(), jsonFixture("fixtures/message-complete.json"));
  }

  @Test
  public void testWithData() throws IOException {
    Message message = Message.newBuilder()
                             .withDestination("2")
                             .withDataPart("key1", "value1")
                             .withDataPart("key2", "value2")
                             .build();

    assertEquals(message.serialize(), jsonFixture("fixtures/message-data.json"));
  }

}
