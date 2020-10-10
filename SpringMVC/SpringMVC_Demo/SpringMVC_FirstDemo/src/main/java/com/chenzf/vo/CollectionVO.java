package com.chenzf.vo;

import java.util.List;
import java.util.Map;

public class CollectionVO {
    private List<String> lists;
    private Map<String, String> map;

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
