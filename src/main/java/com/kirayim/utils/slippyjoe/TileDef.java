package com.kirayim.utils.slippyjoe;

import java.io.File;

/**
 * Created by shalom on 12/03/16.
 */
public class TileDef {
    int xTile;
    int yTile;
    int zoom;
    File file;

    public TileDef(int xTile, int yTile, int zoom) {
        this.xTile = xTile;
        this.yTile = yTile;
        this.zoom = zoom;
    }

    public int getxTile() {
        return xTile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileDef tileDef = (TileDef) o;

        if (xTile != tileDef.xTile) return false;
        if (yTile != tileDef.yTile) return false;
        return zoom == tileDef.zoom;

    }

    @Override
    public int hashCode() {
        int result = xTile;
        result = 31 * result + yTile;
        result = 31 * result + zoom;
        return result;
    }

    public void setxTile(int xTile) {
        this.xTile = xTile;
    }

    public int getyTile() {
        return yTile;
    }

    public void setyTile(int yTile) {
        this.yTile = yTile;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
