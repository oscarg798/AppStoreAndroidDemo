package com.rm.appstoreandroid.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by oscargallon on 5/7/16.
 */
public class AppDTO  implements Serializable{

    private final String name;

    private  List<ImageDTO> images;

    private final String sumary;

    private final String priceCurrency;

    private final String price;

    private final String rights;

    private final String appLink;

    private final String bundleId;

    private final String artist;

    private final String category;

    private final String releaseDate;

    private AppDTO(String name, List<ImageDTO> images,
                   String sumary, String priceCurrency,
                   String price,
                   String rights,
                   String appLink,
                   String bundleId,
                   String artist,
                   String category,
                   String releaseDate) {

        this.name = name;
        this.images = images;
        this.sumary = sumary;
        this.priceCurrency = priceCurrency;
        this.price = price;
        this.rights = rights;
        this.appLink = appLink;
        this.bundleId = bundleId;
        this.artist = artist;
        this.category = category;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public String getSumary() {
        return sumary;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public String getPrice() {
        return price;
    }

    public String getRights() {
        return rights;
    }

    public String getAppLink() {
        return appLink;
    }

    public String getBundleId() {
        return bundleId;
    }

    public String getArtist() {
        return artist;
    }

    public String getCategory() {
        return category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public static class AppDTOBuilder {
        private String name;

        private List<ImageDTO> images;

        private String sumary;

        private String priceCurrency;

        private String price;

        private String rights;

        private String appLink;

        private String bundleId;

        private String artist;

        private String category;

        private String releaseDate;

        public AppDTOBuilder withAName(String name) {
            this.name = name;
            return this;
        }

        public AppDTOBuilder withASumary(String sumary) {
            this.sumary = sumary;
            return this;
        }

        public AppDTOBuilder withAPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
            return this;
        }

        public AppDTOBuilder withAPrice(String price) {
            this.price = price;
            return this;
        }

        public AppDTOBuilder withRights(String rights) {
            this.rights = rights;
            return this;
        }

        public AppDTOBuilder withABundleId(String bundleId) {
            this.bundleId = bundleId;
            return this;
        }

        public AppDTOBuilder withAnAppLink(String appLink) {
            this.appLink = appLink;
            return this;
        }

        public AppDTOBuilder withAnArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public AppDTOBuilder withACategory(String category) {
            this.category = category;
            return this;
        }

        public AppDTOBuilder withAReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public AppDTOBuilder withAnImages(List<ImageDTO> images) {
            this.images = images;
            if (images != null) {
                for (ImageDTO imageDTO : images) {
                    imageDTO.setApp(this.bundleId);
                }
            }
            return this;
        }

        public AppDTO createAppDTO() {
            return new AppDTO(name, images, sumary, priceCurrency, price,
                    rights, appLink, bundleId, artist, category, releaseDate);
        }

    }
}
