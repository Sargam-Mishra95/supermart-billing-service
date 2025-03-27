package com.retailstore.billing.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required.")
    @Size(max = 100, message = "Product name must not exceed 100 characters.")
    private String productName;

    @NotBlank(message = "Product category is required.")
    @Pattern(regexp = "^(Grocery|Electronics|Fashion|Dairy|Others)$", message = "Product category must be one of: Grocery, Electronics, Fashion, Dairy, or Others.")
    private String productCategory;

    @NotNull(message = "Product price is required.")
    @Positive(message = "Product price must be a positive number.")
    private Double productPrice;

    @NotNull(message = "Product quantity is required.")
    @Min(value = 1, message = "Product quantity must be at least 1.")
    private Integer quantity;

}
