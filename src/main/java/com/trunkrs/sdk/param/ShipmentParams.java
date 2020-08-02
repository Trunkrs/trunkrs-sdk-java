package com.trunkrs.sdk.param;

import com.google.gson.annotations.SerializedName;
import com.trunkrs.sdk.TrunkrsSDK;
import com.trunkrs.sdk.exception.ShipmentWithoutParcelsException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.val;

@Builder
public abstract class ShipmentParams {
  public static class ShipmentParamsBuilder {
    private AddressParams pickupAddress;
    private AddressParams deliveryAddress;
    private List<ParcelParams> parcels = new ArrayList<>();

    private APIV1ShipmentParams buildV1() {
      val shipment = new APIV1ShipmentParams();
      shipment.timeSlotId = timeSlotId;

      if (deliveryAddress != null) {
        shipment.setDeliveryAddress(deliveryAddress);
      }
      if (pickupAddress != null) {
        shipment.setPickupAddress(pickupAddress);
      }

      shipment.setParcels(parcels);

      return shipment;
    }

    /**
     * Sets the id of the time slot in which the shipment has to be announced.
     *
     * @param timeSlotId The time slot identifier.
     * @return The builder.
     */
    public ShipmentParamsBuilder timeSlotId(int timeSlotId) {
      this.timeSlotId = timeSlotId;

      return this;
    }

    /**
     * Set the collection pick-up address details.
     *
     * @param address The address to pick-up the shipment from.
     * @return The builder.
     */
    public ShipmentParamsBuilder pickupAddress(AddressParams address) {
      pickupAddress = address;

      return this;
    }

    /**
     * Set the delivery address of the recipient to whom this shipment belongs.
     *
     * @param address The address details of the recipient.
     * @return The builder.
     */
    public ShipmentParamsBuilder deliveryAddress(AddressParams address) {
      deliveryAddress = address;

      return this;
    }

    /**
     * Add a parcel to the shipment.
     *
     * @param parcel The parcel to be added as part of the shipment.
     * @return The builder.
     */
    public ShipmentParamsBuilder addParcel(ParcelParams parcel) {
      parcels.add(parcel);

      return this;
    }

    /**
     * Combine all details into a new instance of {ShipmentParams}.
     *
     * @return The built {@link ShipmentParams} instance.
     * @throws ShipmentWithoutParcelsException Thrown when not parcels have been added to the
     *     builder.
     */
    public ShipmentParams build() throws ShipmentWithoutParcelsException {
      if (parcels.size() == 0) {
        throw new ShipmentWithoutParcelsException();
      }

      switch (TrunkrsSDK.getApiVersion()) {
        case v1:
          return buildV1();
        default:
          return null;
      }
    }
  }

  private int timeSlotId;

  ShipmentParams() {
    timeSlotId = 0;
  }
}

class APIV1ShipmentParams extends ShipmentParams {
  @SerializedName("orderReference")
  String orderReference;

  @SerializedName("timeSlotId")
  int timeSlotId;

  @SerializedName("totalQuantity")
  int nrParcels;

  @SerializedName("pickupName")
  String pickupCompanyName;

  @SerializedName("pickupContact")
  String pickupContactName;

  @SerializedName("pickupAddress")
  String pickupAddress;

  @SerializedName("pickupPostCode")
  String pickupPostalCode;

  @SerializedName("pickupCity")
  String pickupCity;

  @SerializedName("pickupCountry")
  String pickupCountry;

  @SerializedName("pickupEmail")
  String pickupEmail;

  @SerializedName("pickupPhone")
  String pickupPhoneNumber;

  @SerializedName("pickupRemarks")
  String pickupRemarks;

  @SerializedName("deliveryName")
  String deliveryCompanyName;

  @SerializedName("deliveryContact")
  String deliveryContactName;

  @SerializedName("deliveryAddress")
  String deliveryAddress;

  @SerializedName("deliveryPostCode")
  String deliveryPostalCode;

  @SerializedName("deliveryCity")
  String deliveryCity;

  @SerializedName("deliveryCountry")
  String deliveryCountry;

  @SerializedName("deliveryEmail")
  String deliveryEmail;

  @SerializedName("deliveryPhone")
  String deliveryPhoneNumber;

  @SerializedName("deliveryRemarks")
  String deliveryRemarks;

  void setPickupAddress(AddressParams address) {
    pickupCompanyName = address.companyName;
    pickupContactName = address.contactName;
    pickupAddress = address.addressLine;
    pickupPostalCode = address.postalCode;
    pickupCity = address.city;
    pickupCountry = address.countryCode;
    pickupEmail = address.email;
    pickupPhoneNumber = address.phoneNumber;
    pickupRemarks = address.remarks;
  }

  void setDeliveryAddress(AddressParams address) {
    deliveryCompanyName = address.companyName;
    deliveryContactName = address.contactName;
    deliveryAddress = address.addressLine;
    deliveryPostalCode = address.postalCode;
    deliveryCity = address.city;
    deliveryCountry = address.countryCode;
    deliveryEmail = address.email;
    deliveryPhoneNumber = address.phoneNumber;
    deliveryRemarks = address.remarks;
  }

  void setParcels(List<ParcelParams> parcels) {
    val firstParcel = parcels.get(0);
    orderReference = firstParcel.reference;
    nrParcels = parcels.size();
  }
}
