package com.shop.utils.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Description implements Serializable{

    private String nameDesc;
    private String dataDesc;
}