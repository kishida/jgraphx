package com.mxgraph.view;

import java.util.HashMap;
import java.util.Map;

import com.mxgraph.util.mxRectangle;

public class mxTemporaryCellStates {

    /**
     *
     */
    protected mxGraphView view;

    /**
     *
     */
    protected Map<Object, mxCellState> oldStates;

    /**
     *
     */
    protected mxRectangle oldBounds;

    /**
     *
     */
    protected double oldScale;

    /**
     * Constructs a new temporary cell states instance.
     */
    public mxTemporaryCellStates(mxGraphView view) {
        this(view, 1, null);
    }

    /**
     * Constructs a new temporary cell states instance.
     */
    public mxTemporaryCellStates(mxGraphView view, double scale) {
        this(view, scale, null);
    }

    /**
     * Constructs a new temporary cell states instance.
     */
    public mxTemporaryCellStates(mxGraphView view, double scale, Object[] cells) {
        this.view = view;

        // Stores the previous state
        oldBounds = view.getGraphBounds();
        oldStates = view.getStates();
        oldScale = view.getScale();

        // Creates space for the new states
        view.setStates(new HashMap<>());
        view.setScale(scale);

        if (cells != null) {
            mxRectangle bbox = null;

            // Validates the vertices and edges without adding them to
            // the model so that the original cells are not modified
            for (Object cell : cells) {
                mxRectangle bounds = view.getBoundingBox(view.validateCellState(view.validateCell(cell)));
                if (bbox == null) {
                    bbox = bounds;
                } else {
                    bbox.add(bounds);
                }
            }

            if (bbox == null) {
                bbox = new mxRectangle();
            }

            view.setGraphBounds(bbox);
        }
    }

    /**
     * Destroys the cell states and restores the state of the graph view.
     */
    public void destroy() {
        view.setScale(oldScale);
        view.setStates(oldStates);
        view.setGraphBounds(oldBounds);
    }

}
