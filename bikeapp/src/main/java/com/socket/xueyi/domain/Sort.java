package com.socket.xueyi.domain;

import com.google.gson.annotations.Expose;

/**
 * Created by XUEYI on 2015/12/14.
 */
public class Sort {

    @Expose private String director;
    @Expose private String property;
    @Expose private  boolean ignoreCase;
    @Expose private   String nullHandling;
    @Expose private  boolean ascending;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getNullHandling() {
        return nullHandling;
    }

    public void setNullHandling(String nullHandling) {
        this.nullHandling = nullHandling;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
