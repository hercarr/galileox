package mx.hercarr.galileox.rest;

import java.util.List;

import mx.hercarr.galileox.model.Photo;

/**
 * Created by hercarr on 7/13/16.
 */
public class PhotoSearchResponse {

    private List<Photo> hits;

    public List<Photo> getHits() {
        return hits;
    }

    public void setHits(List<Photo> hits) {
        this.hits = hits;
    }

}