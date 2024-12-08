package net.proselyte.trpomax.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToysDTO {

    private Integer id;

    @NotBlank
    private String name;

    @Min(0)
    private BigDecimal price;

    @Min(0)
    private Integer quantity;

    @Min(0)
    private Integer min;

    @Min(0)
    private Integer max;
}
