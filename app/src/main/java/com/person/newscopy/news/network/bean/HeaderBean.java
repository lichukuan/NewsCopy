package com.person.newscopy.news.network.bean;

class HeaderBean {
    /**
     * Status : 1
     * Message : Success
     * Extra :
     */

    private int Status;
    private String Message;
    private String Extra;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String Extra) {
        this.Extra = Extra;
    }
}
