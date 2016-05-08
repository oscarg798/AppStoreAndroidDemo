package com.rm.appstoreandroid.model.dto;

import java.util.List;

/**
 * Created by oscargallon on 5/7/16.
 */
public class AppDTO {

    private final String name;

    private final List<ImageDTO> images;

    private final String sumary;

    private final String priceCurrency;

    private final String price;

    private final String rights;

    private final String appLink;

    private final String bundleId;

    private final String artist;

    private final String category;

    private final String releaseDate;

    public AppDTO(String name, List<ImageDTO> images,
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
}
