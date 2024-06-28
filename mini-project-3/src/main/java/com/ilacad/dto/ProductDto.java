package com.ilacad.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotEmpty(message = "name cannot be empty")
    private String name;
    private String description;
    @DecimalMin(value = "0.0", message = "price must be greater than or equal to 0")
    private BigDecimal price;
    @Min(value = 0, message = "quantity must be greater than or equal to 0")
    private int quantity;
}
