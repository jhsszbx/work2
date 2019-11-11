package com.example.myapplication.work2.staticTable;

public class Home {

    private String imageName;
    private int image;

    public Home(String imageName, int image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Home{" + "imageName='" + imageName + '\'' + ", image=" + image + '}';
    }
}
