package tech.bobliu.assignment06.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean sold;
    private int publisherId;
    private String imageHash;
}
