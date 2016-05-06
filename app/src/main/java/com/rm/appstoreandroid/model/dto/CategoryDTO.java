package com.rm.appstoreandroid.model.dto;

/**
 * Created by oscargallon on 5/5/16.
 */
public class CategoryDTO {
    private final String id;

    private final String term;

    private final String label;

    public String scheme;

    public CategoryDTO(String id, String term, String label, String scheme) {
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

    /**
     * Builder for categories
     */
    public static class CategoryDTOBuilder {

        public String id;

        public String term;

        public String label;

        public String scheme;

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

        public CategoryDTO createCategoryDTO(){
            return new CategoryDTO(id,term,label, scheme);
        }

    }
}
