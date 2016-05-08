package com.rm.appstoreandroid.model.dto;

/**
 * Created by oscargallon on 5/7/16.
 */
public class ImageDTO {

    private final String link;
    private final String attributes;

    public ImageDTO(String link, String attributes) {
        this.link = link;
        this.attributes = attributes;
    }

    public static class ImageDTOBuilder{
        private  String link;
        private  String attributes;

        public ImageDTOBuilder withALink(String link) {
            this.link = link;
            return this;
        }

        public ImageDTOBuilder withAnAttributes(String attributes) {
            this.attributes = attributes;
            return this;
        }
    }
}
