package com.example.photofilter;

public class Photo {

    long id;
    String uri;
    String name;
    String date;
    int size;
    long bucketId;
    String bucketName;

    public Photo(long id, String uri, String name, String date, int size, long bucketId, String bucketName) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.date = date;
        this.size = size;
        this.bucketId = bucketId;
        this.bucketName = bucketName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
