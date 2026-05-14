package org.yaa;

public class DVD extends Item {
    private String title;
    private String DVDDirector;
    private int  DVDDuration;

    public DVD(String title, String DVDDirector, int DVDDuration) {
        super();
        this.title = title;
        this.DVDDirector = DVDDirector;
        this.DVDDuration = DVDDuration;
    }
}
