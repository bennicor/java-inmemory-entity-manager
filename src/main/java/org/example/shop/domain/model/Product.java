package org.example.shop.domain.model;


public class Product {
    private Integer id;
    private String code;
    private String name;
    private Float price;
    private Float weightKg;
    private Integer lengthCm, widthCm, heightCm;
    private String description;

    public Product(Integer id, String code, String name, Float price, Float weightKg,
                   Integer lengthCm, Integer widthCm, Integer heightCm, String description) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.weightKg = weightKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.description = description;
    }

    public Product(String code, String name, Float price, Float weightKg,
                   Integer lengthCm, Integer widthCm, Integer heightCm, String description) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.weightKg = weightKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.description = description;
    }

    public Product() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Float getPrice() { return price; }
    public void setPrice(Float price) { this.price = price; }

    public Float getWeightKg() { return weightKg; }
    public void setWeightKg(Float weightKg) { this.weightKg = weightKg; }

    public int getLengthCm() { return lengthCm; }
    public void setLengthCm(int lengthCm) { this.lengthCm = lengthCm; }

    public int getWidthCm() { return widthCm; }
    public void setWidthCm(int widthCm) { this.widthCm = widthCm; }

    public int getHeightCm() { return heightCm; }
    public void setHeightCm(int heightCm) { this.heightCm = heightCm; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
