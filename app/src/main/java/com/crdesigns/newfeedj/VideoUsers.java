package com.crdesigns.newfeedj;

public class VideoUsers {
    private int id;
    private String url, url_edge, watched;

    public VideoUsers(String url, String url_edge, String watched) {
        this.url = url;
        this.url_edge = url_edge;
        this.watched = watched;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_edge() {
        return url_edge;
    }

    public void setUrl_edge(String url_edge) {
        this.url_edge = url_edge;
    }

    public String getWatched() {
        return watched;
    }

    public void setWatched(String watched) {
        this.watched = watched;
    }
}
