package mx.hercarr.photofinder.rest;

import java.util.List;

import mx.hercarr.photofinder.model.Photo;

public class PhotoSearchResponse {

    private List<Photo> hits;

    public List<Photo> getHits() {
        return hits;
    }

    public void setHits(List<Photo> hits) {
        this.hits = hits;
    }

}