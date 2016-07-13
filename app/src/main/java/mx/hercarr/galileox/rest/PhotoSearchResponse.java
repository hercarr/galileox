package mx.hercarr.galileox.rest;

import java.util.List;

import mx.hercarr.galileox.model.Photo;

/**
 * Created by hercarr on 7/13/16.
 */
public class PhotoSearchResponse {

    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}