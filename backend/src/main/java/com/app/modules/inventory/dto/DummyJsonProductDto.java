package com.app.modules.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DummyJsonProductDto {
  private Long id;
  private String title;
  private String description;
  private String category;
  private BigDecimal price;

  @JsonProperty("discountPercentage")
  private BigDecimal discountPercentage;

  private BigDecimal rating;
  private Integer stock;
  private List<String> tags;
  private String brand;
  private String sku;
  private Integer weight;

  private DimensionsDto dimensions;

  @JsonProperty("warrantyInformation")
  private String warrantyInformation;

  @JsonProperty("shippingInformation")
  private String shippingInformation;

  @JsonProperty("availabilityStatus")
  private String availabilityStatus;

  private List<ReviewDto> reviews;

  @JsonProperty("returnPolicy")
  private String returnPolicy;

  @JsonProperty("minimumOrderQuantity")
  private Integer minimumOrderQuantity;

  private MetaDto meta;
  private List<String> images;
  private String thumbnail;

  @Data
  public static class DimensionsDto {
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal depth;
  }

  @Data
  public static class ReviewDto {
    private Integer rating;
    private String comment;
    private String date;

    @JsonProperty("reviewerName")
    private String reviewerName;

    @JsonProperty("reviewerEmail")
    private String reviewerEmail;
  }

  @Data
  public static class MetaDto {
    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
    private String barcode;

    @JsonProperty("qrCode")
    private String qrCode;
  }
}
