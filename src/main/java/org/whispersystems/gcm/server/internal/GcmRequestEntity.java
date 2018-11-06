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
package org.whispersystems.gcm.server.internal;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "message")
public class GcmRequestEntity {
  @JsonProperty(value = "data")
  private Map<String, String> data;

  @JsonProperty(value = "token")
  private String token;

  @JsonProperty(value = "notification")
  private Map<String, String> notification;

  @JsonRawValue
  private String android;

  @JsonRawValue
  private String apns;

  public GcmRequestEntity(Map<String, String> data, String token,
                          Map<String, String> notification,
                          String android, String apns)
  {
    this.data            = data;
    this.token           = token;
    this.notification    = notification;
    this.android         = android;
    this.apns            = apns;
  }
}
