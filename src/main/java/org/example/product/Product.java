package org.example.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Data
@AllArgsConstructor
@Slf4j
@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private double price;

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        Product product = (Product) o;
        return this.id == product.getId();
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
