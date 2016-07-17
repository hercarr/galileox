package mx.hercarr.galileox.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hercarr on 7/13/16.
 */
public class Photo {

    @SerializedName("id")
    private Long id;
    @SerializedName("tags")
    private String tags;
    @SerializedName("likes")
    private Integer likes;
    @SerializedName("downloads")
    private Integer downloads;
    @SerializedName("webformatURL")
    private String imageUrl;
    @SerializedName("pageURL")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}