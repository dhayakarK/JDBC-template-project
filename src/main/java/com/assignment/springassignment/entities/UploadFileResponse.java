package com.assignment.springassignment.entities;

public class UploadFileResponse {
   // private int Id;
    private String filename;
   // private String fileDownloadUri;
    private long size;

    public UploadFileResponse( String filename,  long size) {

        this.filename = filename;
       // this.fileDownloadUri = fileDownloadUri;
        this.size = size;
    }
}
