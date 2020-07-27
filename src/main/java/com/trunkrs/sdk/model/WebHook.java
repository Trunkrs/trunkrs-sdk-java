package com.trunkrs.sdk.model;

import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.enumeration.EventType;
import com.trunkrs.sdk.exception.GeneralApiException;
import com.trunkrs.sdk.exception.NotAuthorizedException;
import com.trunkrs.sdk.exception.ServerValidationException;
import com.trunkrs.sdk.exception.UnsupportedVersionException;
import com.trunkrs.sdk.net.ApiResource;
import com.trunkrs.sdk.param.WebHookParams;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

public abstract class WebHook extends ApiResource {
  /**
   * Registers a new web hook subscription based the on specified parameters.
   * @param params The web hook subscription parameters.
   * @return The newly created web hook.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws ServerValidationException Thrown when the shipment param don't match the expectation of Trunkrs.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(UnsupportedVersionException.class)
  public static WebHook register(WebHookParams params)
    throws NotAuthorizedException, GeneralApiException, ServerValidationException {
    WebHook webHook = null;
    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        webHook = post("webhooks", params, APIV1WebHook.class);
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return webHook;
  }

  /**
   * Unregisters the web hook subscription by the specified identifier.
   * @param webhookId The web hook identifier.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public static void unregister(int webhookId)
    throws NotAuthorizedException, GeneralApiException {
    delete(String.format("webhooks/%d", webhookId));
  }

  /**
   * Gets all active web hook subscriptions.
   * @return A list of all web hook subscriptions.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  @SneakyThrows(UnsupportedVersionException.class)
  public static List<WebHook> retrieve()
    throws NotAuthorizedException, GeneralApiException {
    WebHook[] webHooks = null;
    switch (TrunkrsSDK.getApiVersion()) {
      case v1:
        webHooks = get("webhooks", APIV1WebHook[].class);
        break;
      case v2:
        // Stub for V2, can never be called at this moment.
        throw new UnsupportedVersionException();
    }

    return Arrays.asList(webHooks);
  }

  /**
   * Gets the web hook identifier.
   * @return The web hook identifier.
   */
  public abstract int getId();

  /**
   * Gets the callback URL to be notified.
   * @return The callback URL to notify.
   */
  public abstract String getCallbackUrl();

  /**
   * Gets the header name to communicate the session key.
   * @return The header name.
   */
  public abstract String getSessionHeaderName();

  /**
   * The event type on which to notify the web hook.
   * @return The event type which has been subscribed to.
   */
  public abstract EventType getEventType();

  /**
   * Unregisters the web hook subscription by the specified identifier.
   * @throws NotAuthorizedException Thrown when the credentials are invalid, not set or expired.
   * @throws GeneralApiException Thrown when the API responds with an unexpected answer.
   */
  public void unregister()
    throws NotAuthorizedException, GeneralApiException {
    WebHook.unregister(getId());
  }
}
