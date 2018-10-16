package com.example.rfgr.italianstuff;

public class Pizza {
    private String name;
    private int imageResourceId;

    public static final Pizza[] pizzas = {
            new Pizza("Diavolo", R.drawable.ic_launcher_background),
            new Pizza("Funghi", R.drawable.ic_launcher_background),
            new Pizza("Trzecia", R.drawable.ic_launcher_background),
            new Pizza("Czwarta", R.drawable.ic_launcher_background),
            new Pizza("Piąta i ostatnia", R.drawable.ic_launcher_background)
    };

    private Pizza(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
}
