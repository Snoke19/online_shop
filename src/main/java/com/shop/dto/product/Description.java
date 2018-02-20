package com.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Description implements Serializable{

    private int id;
    private String nameDesc;
    private String dataDesc;
}