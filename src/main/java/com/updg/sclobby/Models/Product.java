package com.updg.sclobby.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Alex
 * Date: 12.04.2014  15:12
 */
@Getter
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String desc;
    private int where;
    private int active;
    private int nLevel;
    private float money;
    private float bubliks;
    private int exp;
}
