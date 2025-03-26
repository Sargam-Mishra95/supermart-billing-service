package com.retailstore.billing.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for submitting customer-related data and product information.")
public class BillRequest {

    @NotBlank(message = "Customer name is required.")
    @Size(max = 100, message = "Customer name must not exceed 100 characters.")
    private String customerName;

    @NotBlank(message = "Customer email is required.")
    @Email(message = "Please provide a valid email address.")
    private String customerEmail;

    @NotNull(message = "Customer mobile number is required.")
    @Min(value = 1000000000L, message = "Customer mobile number must be at least 10 digits.")
    @Max(value = 9999999999L, message = "Customer mobile number must be a valid 10-digit number.")
    private Integer customerMobNumber;

    @NotBlank(message = "Customer type is required.")
    @Pattern(regexp = "^(employee|customer|admin)$", message = "Customer type must be one of: employee, customer, or admin.")
    private String customerType;

    @NotBlank(message = "Original currency is required.")
    @Size(min = 3, max = 3, message = "Original currency must be a 3-character currency code.")
    private String originalCurrency;

    @NotBlank(message = "Target currency is required.")
    @Size(min = 3, max = 3, message = "Target currency must be a 3-character currency code.")
    private String targetCurrency;

    @NotNull(message = "Product request list cannot be null.")
    @Size(min = 1, message = "At least one product is required in the product request.")
    private List<ProductRequest> productRequest;

}
