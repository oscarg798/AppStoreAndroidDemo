package com.rm.appstoreandroid.model.dto;

import java.io.Serializable;

/**
 * Created by oscargallon on 5/5/16.
 */
public class CategoryDTO  implements Serializable{

    /**
     * Id de la categoria
     */
    private final String id;

    /**
     * Nombre de la aplicacion
     */
    private final String term;

    /**
     * Nombre a mostrar de la aplicacion
     */
    private final String label;

    /**
     * Referencia a el icono de la aplicacion
     */
    private final int image;

    /**
     * Scheme
     */
    public String scheme;

    /**
     *
     * @param image  Referencia a el icono de la aplicacion
     * @param id Id de la categoria
     * @param term Nombre de la aplicacion
     * @param label Nombre a mostrar de la aplicacion
     * @param scheme  Scheme
     */
    public CategoryDTO(int image, String id, String term, String label, String scheme) {
        this.image = image;
        this.id = id;
        this.term = term;
        this.label = label;
        this.scheme = scheme;
    }

    public String getId() {
        return id;
    }

    public String getTerm() {
        return term;
    }

    public String getLabel() {
        return label;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public int getImage() {
        return image;
    }

    /**
     * Builder for categories
     */
    public static class CategoryDTOBuilder {

        public String id;

        public String term;

        public String label;

        public String scheme;

        public int image;

        public CategoryDTOBuilder withAnId(String id) {
            this.id = id;
            return this;
        }

        public CategoryDTOBuilder withATerm(String term) {
            this.term = term;
            return this;
        }

        public CategoryDTOBuilder withALabel(String label) {
            this.label = label;
            return this;
        }

        public CategoryDTOBuilder withAScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public CategoryDTOBuilder withAnImage(int image) {
            this.image = image;
            return this;
        }

        public CategoryDTO createCategoryDTO() {
            return new CategoryDTO(image, id, term, label, scheme);
        }

    }
}
