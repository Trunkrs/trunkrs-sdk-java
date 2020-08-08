package com.trunkrs.sdk.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Serializer {
  /**
   * Gets a serializer instance.
   *
   * @return The serializer instance.
   */
  public static Serializer get() {
    return new JsonSerializer();
  }

  /**
   * Serializes the object into string representation.
   *
   * @param model The model to be serialized.
   * @param <Model> The model type to be serialized.
   * @return The serialized representation of the object.
   */
  public abstract <Model> String serialize(Model model);

  /**
   * Deserializes the string representation into an object.
   *
   * @param serialized The serialized representation.
   * @param modelClass The class reference of the model.
   * @param <Model> The target model to be de
   * @return The object representation of the serialized input.
   */
  public abstract <Model> Model deserialize(String serialized, Class<Model> modelClass);
}

class JsonSerializer extends Serializer {
  private static final Gson GSON = buildGson();

  private static Gson buildGson() {
    return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
  }

  @Override
  public <Model> String serialize(Model model) {
    return GSON.toJson(model);
  }

  @Override
  public <Model> Model deserialize(String serialized, Class<Model> modelClass) {
    return GSON.fromJson(serialized, modelClass);
  }
}
