package com.quangtd.photoeditor.model.response;

import java.util.List;

/**
 * @author ToanNS
 */

public class AlbumImage {
    private List<LocalImage> localImages;
    private String name;
    private String path;

    public AlbumImage(List<LocalImage> localImages, String name) {
        this.localImages = localImages;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<LocalImage> getLocalImages() {
        return localImages;
    }

    public String getName() {
        return name;
    }

    public String getFirstImage() {
        if (localImages != null && !localImages.isEmpty()) {
            return localImages.get(0).getPath();
        }
        return null;
    }
}
