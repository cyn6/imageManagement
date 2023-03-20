package com.cyn.pojo;

public class Disease {
    private Integer id;
    private String cropName;
    private String diseaseName;
    private String imagePath;
    private String addTime;

    @Override
    public String toString() {
        return "Disease{" +
                "id=" + id +
                ", cropName='" + cropName + '\'' +
                ", diseaseName='" + diseaseName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}