package com.example.myapplication.work2.staticTable;

public class Person {

    private String name;
    private int image;
    private int arrows;

    public Person(String name, int image, int arrows) {
        this.name = name;
        this.image = image;
        this.arrows = arrows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getArrows() {
        return arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", image=" + image + ", arrows=" + arrows + '}';
    }
}
