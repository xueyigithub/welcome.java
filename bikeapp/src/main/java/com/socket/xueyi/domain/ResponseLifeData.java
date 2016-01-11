package com.socket.xueyi.domain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XUEYI on 2015/11/25.
 */
public class ResponseLifeData implements Serializable{

    @Expose private List<LifeGuide> content;
    @Expose private boolean last;
    @Expose private int totalPages;
    @Expose private int totalElements;
    @Expose private int number;
    @Expose private int size;
    @Expose private List<Sort> sort;
    @Expose private boolean first;
    @Expose private int numberOfElements;

    public List<LifeGuide> getContent() {
        return content;
    }

    public void setContent(List<LifeGuide> content) {
        this.content = content;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "content=" + content +
                ", last=" + last +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", number=" + number +
                ", size=" + size +
                ", sort='" + sort + '\'' +
                ", first=" + first +
                ", numberOfElements=" + numberOfElements +
                '}';
    }
}