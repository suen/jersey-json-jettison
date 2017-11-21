package com.example.json;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Dog extends Animal {

    public String food;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

}
