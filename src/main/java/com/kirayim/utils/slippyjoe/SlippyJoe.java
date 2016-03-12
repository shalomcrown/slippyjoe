package com.kirayim.utils.slippyjoe;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;

/**
 * Created by shalom on 12/03/16.
 */

public class SlippyJoe extends JPanel {

    Point2D.Double mapCenter;
    double zoom;
    public static final int tileSize = 256;


    //=====================================================

    public SlippyJoe(Point2D.Double mapCenter, double initialZoom) {
        super();

        this.zoom = initialZoom;
        this.mapCenter = mapCenter;
    }


    //======================================================


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Dimension dim = getSize();

        for (int x = 0; x < dim.getWidth() + tileSize; x += tileSize) {
            for (int y = 0; y < dim.getHeight() + tileSize; y += tileSize) {
                Point2D.Double coords = pixelToLatLon(x, y, zoom, mapCenter, dim);


            }
        }


    }


    //==============================================================

    public static Point2D.Double pixelToLatLon(int x, int y, double zoom, Point2D.Double mapCenter, Dimension panelSize) {
        double xPixelsFromCenter = x - panelSize.getWidth() / 2.;
        double yPixeldFromCenter = y - panelSize.getHeight() / 2.;

        double pixelWidth = 360. / (Math.pow(2, zoom) * tileSize);
        double pixelHeight = pixelWidth; // TODO: 360. / Math.pow(2, zoom + 8) * WGS84Correction

        double xCoord = mapCenter.x - xPixelsFromCenter * pixelWidth;
        double yCoord = mapCenter.y - yPixeldFromCenter * pixelHeight;

        return new Point2D.Double(xCoord, yCoord);
    }


    public static  Point2D.Double latLonToPixel(Point2D.Double latLon, double zoom, Point2D.Double mapCenter, Dimension panelSize) {
        double pixelWidth = 360. / (Math.pow(2, zoom) * tileSize);
        double pixelHeight = pixelWidth; // TODO: 360. / Math.pow(2, zoom + 8) * WGS84Correction

        double xCoord = (mapCenter.x - latLon.x) / pixelWidth - panelSize.getWidth() / 2.;
        double yCoord = (mapCenter.y - latLon.y) / pixelHeight - panelSize.getHeight() / 2.;

        return new Point2D.Double(xCoord, yCoord);
    }


    //=================================================================

    public static TileDef getTileNumber(final double lat, final double lon, final int zoom) {
        int xtile = (int)Math.floor( (lon + 180) / 360 * (1<<zoom) ) ;
        int ytile = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1<<zoom) ) ;
        if (xtile < 0)
            xtile=0;
        if (xtile >= (1<<zoom))
            xtile=((1<<zoom)-1);
        if (ytile < 0)
            ytile=0;
        if (ytile >= (1<<zoom))
            ytile=((1<<zoom)-1);

        return new TileDef(xtile, ytile, zoom);
    }


    class BoundingBox {
        double north;
        double south;
        double east;
        double west;
    }

    BoundingBox tile2boundingBox(final int x, final int y, final int zoom) {
        BoundingBox bb = new BoundingBox();
        bb.north = tile2lat(y, zoom);
        bb.south = tile2lat(y + 1, zoom);
        bb.west = tile2lon(x, zoom);
        bb.east = tile2lon(x + 1, zoom);
        return bb;
    }

    static double tile2lon(int x, int z) {
        return x / Math.pow(2.0, z) * 360.0 - 180;
    }

    static double tile2lat(int y, int z) {
        double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
        return Math.toDegrees(Math.atan(Math.sinh(n)));
    }

}
