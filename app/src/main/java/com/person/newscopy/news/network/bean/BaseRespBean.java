package com.person.newscopy.news.network.bean;

class BaseRespBean {
    /**
     * StatusMessage : success
     * StatusCode : 0
     */

    private String StatusMessage;
    private int StatusCode;

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String StatusMessage) {
        this.StatusMessage = StatusMessage;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }
}
