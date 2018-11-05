/**
 * Copyright (C) 2015 Open Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.whispersystems.gcm.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.whispersystems.gcm.server.internal.GcmRequestEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Message {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final Map<String, String> data;
  private final String              token;
  private final Map<String, String> notification;
  private final String              android;
  private final String              apns;

  private Message(Map<String, String> data, String token,
                  Map<String, String> notification,
                  String android, String apns)
  {
    this.data            = data;
    this.token           = token;
    this.notification    = notification;
    this.android         = android;
    this.apns            = apns;
  }

  public String serialize() throws JsonProcessingException {
    GcmRequestEntity requestEntity = new GcmRequestEntity(data, token, notification, android, apns);

    return objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE).writeValueAsString(requestEntity);
  }

  /**
   * Construct a new Message using a Builder.
   * @return A new Builder.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private Map<String, String> data            = null;
    private String              token           = null;
    private Map<String, String> notification    = null;
    private String              android         = null;
    private String              apns            = null;

    private Builder() {}

    /**
     * Set a key in the GCM JSON payload delivered to the application (optional).
     * @param key The key to set.
     * @param value The value to set.
     * @return The Builder.
     */
    public Builder withDataPart(String key, String value) {
      if (data == null) {
        data = new HashMap<>();
      }
      data.put(key, value);
      return this;
    }

    /**
     * Set the destination GCM registration ID (mandatory).
     * @param registrationId The destination GCM registration ID.
     * @return The Builder.
     */
    public Builder withDestination(String registrationId) {
      this.token = registrationId;
      return this;
    }

    /**
     * Set a key in the GCM JSON notification payload delivered to the application (optional).
     * @param key The key to set.
     * @param value The value to set.
     * @return The Builder.
     */
    public Builder withNotificationPart(String key, String value) {
      if (notification == null) {
        notification = new HashMap<>();
      }
      notification.put(key, value);
      return this;
    }

    /**
     * Set a key in the GCM JSON android payload delivered to the application (optional).
     * @return The Builder.
     */
    public Builder withAndroid(String android) {
      this.android = android;
      return this;
    }

    /**
     * Set a key in the GCM JSON APNS (Apple Push Notification Service) payload delivered to the application (optional).
     * @param apns The value to set.
     * @return The Builder.
     */
    public Builder withApns(String apns) {
      this.apns = apns;
      return this;
    }

    /**
     * Construct a message object.
     *
     * @return An immutable message object, as configured by this builder.
     */
    public Message build() {
      if (token.isEmpty()) {
        throw new IllegalArgumentException("You must specify a destination!");
      }

      return new Message(data, token, notification, android, apns);
    }

  }

}
