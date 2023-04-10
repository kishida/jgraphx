package com.mxgraph.shape;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

public class mxTriangleShape extends mxBasicShape {

    /**
     *
     */
    @Override
    public Shape createShape(mxGraphics2DCanvas canvas, mxCellState state) {
        Rectangle temp = state.getRectangle();
        int x = temp.x;
        int y = temp.y;
        int w = temp.width;
        int h = temp.height;
        String direction = mxUtils.getString(state.getStyle(),
                mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_EAST);
        Polygon triangle = new Polygon();

        switch (direction) {
            case mxConstants.DIRECTION_NORTH -> {
                triangle.addPoint(x, y + h);
                triangle.addPoint(x + w / 2, y);
                triangle.addPoint(x + w, y + h);
            }
            case mxConstants.DIRECTION_SOUTH -> {
                triangle.addPoint(x, y);
                triangle.addPoint(x + w / 2, y + h);
                triangle.addPoint(x + w, y);
            }
            case mxConstants.DIRECTION_WEST -> {
                triangle.addPoint(x + w, y);
                triangle.addPoint(x, y + h / 2);
                triangle.addPoint(x + w, y + h);
            }
            default -> {
                triangle.addPoint(x, y);
                triangle.addPoint(x + w, y + h / 2);
                triangle.addPoint(x, y + h);
            }
        }
        // EAST

        return triangle;
    }

}
