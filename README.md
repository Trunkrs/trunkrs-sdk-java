# Trunkrs SDK for Java

![CI](https://github.com/Trunkrs/trunkrs-sdk-java/workflows/CI/badge.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/Trunkrs/trunkrs-sdk-java/badge.svg)](https://coveralls.io/github/Trunkrs/trunkrs-sdk-java)
[![Maven Central](https://img.shields.io/maven-central/v/com.trunkrs/sdk)](https://mvnrepository.com/artifact/com.trunkrs/sdk)
![License](https://poser.pugx.org/trunkrs/sdk/license)

The Trunkrs software development kit for the public client SDK. With this Java SDK you can manage your shipments, shipment states and webhooks within our system.

## Requirements

Java 1.8 and later.

## Installation

You can install the SDK through Maven or Gradle. Simply add the SDK to your project dependencies.

#### Gradle:
```gradle
dependencies {
    implementation group: "com.trunkrs", name: "sdk", version: "1.1.0"
}
```

#### Maven:
```xml
<dependencies>
  <dependency>
    <groupId>com.trunkrs</groupId>
    <artifactId>sdk</artifactId>
    <version>1.1.0</version>
  </dependency>
</dependencies>
```

## Getting started

Setup the SDK settings before usage by supplying your merchant credentials. If you don't have any credentials yet, please contact [Trunkrs](https://trunkrs.nl) for more information.

```java
final APICredentials creds = APICredentials.from("your-client-id", "your-secret");

TrunkrsSDK.setCredentials(creds);
```

### Using staging

To make use of the Trunkrs staging environment, which has been supplied to test your implementation with our system.
The SDK can be switched easily.

```java
TrunkrsSDK.useStaging();
```

Both API endpoints and the tracking URL's will point to the staging environment.

The SDK can be easily switched back to production mode as well:
```java
TrunkrsSDK.useProduction();
```

## Shipments

### Create a shipment

A shipment can be created through the `Shipment` class. It exposes a static method `Shipment.create(...)`. To create the details about a shipment the `ShipmentParams` and its builder class can be used.

```java
final int timeSlotId = 100; // Can be found using the TimeSlot class
final ParcelParams parcelDetails = ParcelParams.builder()
    .reference("your-parcel-reference")
    .build();
final AddressParams recipientAddress = AddressParams.builder()
    .contactName("recipient-name")
    .addressLine("recipient-address-line")
    .postalCode("recipient-postal")
    .city("recipient-city")
    .countryCode("NL")
    .email("person@email.com")
    .build();
final AddressParams pickupAddress = AddressParams.builder()
    .contactName("your-name")
    .addressLine("your-address-line")
    .postalCode("your-postal")
    .city("your-city")
    .countryCode("NL")
    .email("you@yourcompany.com")
    .build();
final ShipmentParams shipment = ShipmentParams.builder()
    .addParcel(parcelDetails)
    .pickupAddress(pickupAddress)
    .deliveryAddress(recipientAddress)
    .timeSlotId(timeSlotId)
    .build();

final List<Shipment> shipments = Shipment.create(shipment);
```

### Retrieve shipment details

Details for a single shipment can be retrieved through its identifier by calling the `Shipment.find(shipmentId)` method.

```java
final Shipment shipment = Shipment.find(shipmentId);
```

### Retrieve shipment history

Your shipment history can be listed in a paginated manner by using the `Shipment.retrieve(page)` method.
Every returned page contains a maximum of 50 shipments.

```java
final List<Shipment> shipments = Shipment.retrieve(page);
```

### Cancel a shipment

Shipments can be canceled by their identifier or simply through the `cancel()` method on an instance of a `Shipment`.

The `Shipment` class exposes the `cancel(shipmentId)` static method:
```java
Shipment.cancel(shipmentId);
```

An instance of the `Shipment` class also exposes a convenience method `cancel()`:

```java
final Shipment shipment = Shipment.find(shipmentId);

shipment.cancel();
```

## Shipment State

To retrieve details about the shipment's current state and the current owner of the shipment.
The `ShipmentState` class can be used which exposes the static `find(shipmentId)` method.

```java
final ShipmentState state = ShipmentState.find(shipmentId);
```

An instance of the `Shipment` class also exposes a convenience method `getState()`:

```java
final Shipment shipment = Shipment.find(shipmentId);
final ShipmentState state = shipment.getState();
```

## Web hooks

To be notified about shipment state changes, Trunkrs has created a webhook notification service.
The SDK allows the registration of a callback URL for notifications through this service.

### Register a web hook

The `WebHook` class exposes a static method called `register(webHookParams)` which allows the registration of new web hooks:

```java
final WebHookParams webHookParams = WebHookParams.builder()
    .callbackUrl("https://your.web.service/shipments/webhook")
    .headerName("X-SESSION-TOKEN")
    .sessionToken("your-secret-session-token")
    .event(EventType.onStateUpdate)
    .build();

final WebHook webHook = WebHook.register(webHookParams);
```

### List all active web hooks

The `WebHook` class exposes a method to list all your currently active web hooks you registered with Trunkrs.

```java
final List<WebHook> webHooks = WebHook.retrieve();
```

### Unregister/delete a web hook

The `WebHook` class lets you delete active web hook subscriptions through the `unregister(webHookId)` method.

```java
WebHook.unregister(WebHookId);
```

An instance of the `WebHook` class also exposes a convenience method `unregister()`:

```java
final List<WebHook> webHooks = WebHook.retrieve();
final WebHook firstWebHook = webHooks.get(0);

firstWebHook.unregister();
```

## Labels

### Retrieve the label for a single shipment

The `Label` for a single shipment can be retrieved by a combination of the Trunkrs number and the postal code.
Both ZPL and PDF labels are supported through this interface.

```java
final Label pdfLabel = Label.download(trunkrsNr, postalCode, LabelType.PDF);
final Label zplLabel = Label.download(trunkrsNr, postalCode, LabelType.ZPL);
```

### Batch retrieve multiple shipment labels

Retrieve labels for multiple shipments by supplying a list of Trunkrs numbers.

```java
final Label batchedLabel = Label.downloadBatch(trunkrsNrs, LabelType.PDF);
```

> At this moment only PDF labels are supported when using the batching endpoint.
> Every label will be placed on its own page in a single document.
