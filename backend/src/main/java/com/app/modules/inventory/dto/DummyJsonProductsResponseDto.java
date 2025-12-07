package com.app.modules.inventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class DummyJsonProductsResponseDto {
  private List<DummyJsonProductDto> products;
  private Integer total;
  private Integer skip;
  private Integer limit;
}
