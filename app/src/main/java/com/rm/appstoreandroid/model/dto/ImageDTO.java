package com.rm.appstoreandroid.model.dto;

import java.io.Serializable;

/**
 * Created by oscargallon on 5/7/16.
 */
public class ImageDTO  implements Serializable{

    /**
     * Link de la imagen
     */
    private final String link;

    /**
     * Altura de la imagen
     */
    private final String attributes;

    /**
     * Bundle id de la aplicacion
     */
    private String app;

    /**
     *
     * @param link  Link de la imagen
     * @param attributes Altura de la imagen
     * @param app Bundle id de la aplicacion
     */
    private ImageDTO(String link, String attributes, String app) {
        this.link = link;
        this.attributes = attributes;
        this.app = app;
    }

    public String getLink() {
        return link;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    /**
     * Patron builder de la imagen
     */
    public static class ImageDTOBuilder {
        private String link;
        private String attributes;
        private String app;

        public ImageDTOBuilder withALink(String link) {
            this.link = link;
            return this;
        }

        public ImageDTOBuilder withAnAttributes(String attributes) {
            this.attributes = attributes;
            return this;
        }

        public ImageDTOBuilder withAnApp(String app) {
            this.app = app;
            return this;
        }

        public ImageDTO createImageDTO() {
            return new ImageDTO(link, attributes, app);
        }
    }
}
