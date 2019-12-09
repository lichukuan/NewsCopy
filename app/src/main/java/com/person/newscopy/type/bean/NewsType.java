package com.person.newscopy.type.bean;

import java.util.List;

public class NewsType {

    /**
     * code : 1
     * result : ["news_all","hot","tech","world","politics","lottery","sports","society","home","internet","software","smart_home","entertainment","movie","teleplay","shows","gossip","game","nba","car","finance","stock","funny","military","baby","food","fashion","discovery","regimen","history","essay","travel","hot_gallery","old_picture","gallery_photograthy"]
     */

    private int code;
    private List<String> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
