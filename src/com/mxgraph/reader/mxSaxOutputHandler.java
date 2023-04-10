package com.mxgraph.reader;

import java.util.Hashtable;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mxgraph.canvas.mxICanvas2D;

/**
 * XMLReader reader = SAXParserFactory.newInstance().newSAXParser()
 * .getXMLReader(); reader.setContentHandler(new mxSaxExportHandler( new
 * mxGraphicsExportCanvas(g2))); reader.parse(new InputSource(new
 * StringReader(xml)));
 */
public class mxSaxOutputHandler extends DefaultHandler {

    /**
     *
     */
    protected mxICanvas2D canvas;

    /**
     *
     */
    protected transient Map<String, IElementHandler> handlers = new Hashtable<>();

    /**
     *
     */
    public mxSaxOutputHandler(mxICanvas2D canvas) {
        setCanvas(canvas);
        initHandlers();
    }

    /**
     * Sets the canvas for rendering.
     */
    public void setCanvas(mxICanvas2D value) {
        canvas = value;
    }

    /**
     * Returns the canvas for rendering.
     */
    public mxICanvas2D getCanvas() {
        return canvas;
    }

    /**
     *
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        IElementHandler handler = handlers.get(qName.toLowerCase());

        if (handler != null) {
            handler.parseElement(atts);
        }
    }

    /**
     *
     */
    protected void initHandlers() {
        handlers.put("save", (IElementHandler) (Attributes atts) -> {
            canvas.save();
        });

        handlers.put("restore", (IElementHandler) (Attributes atts) -> {
            canvas.restore();
        });

        handlers.put("scale", (IElementHandler) (Attributes atts) -> {
            canvas.scale(Double.parseDouble(atts.getValue("scale")));
        });

        handlers.put("translate", (IElementHandler) (Attributes atts) -> {
            canvas.translate(Double.parseDouble(atts.getValue("dx")),
                    Double.parseDouble(atts.getValue("dy")));
        });

        handlers.put("rotate", (IElementHandler) (Attributes atts) -> {
            canvas.rotate(Double.parseDouble(atts.getValue("theta")), atts
                    .getValue("flipH").equals("1"), atts.getValue("flipV")
                            .equals("1"), Double.parseDouble(atts.getValue("cx")),
                            Double.parseDouble(atts.getValue("cy")));
        });

        handlers.put("strokewidth", (IElementHandler) (Attributes atts) -> {
            canvas.setStrokeWidth(Double.parseDouble(atts.getValue("width")));
        });

        handlers.put("strokecolor", (IElementHandler) (Attributes atts) -> {
            canvas.setStrokeColor(atts.getValue("color"));
        });

        handlers.put("dashed", (IElementHandler) (Attributes atts) -> {
            String temp = atts.getValue("fixDash");
            boolean fixDash = temp != null && temp.equals("1");
            
            canvas.setDashed(atts.getValue("dashed").equals("1"), fixDash);
        });

        handlers.put("dashpattern", (IElementHandler) (Attributes atts) -> {
            canvas.setDashPattern(atts.getValue("pattern"));
        });

        handlers.put("linecap", (IElementHandler) (Attributes atts) -> {
            canvas.setLineCap(atts.getValue("cap"));
        });

        handlers.put("linejoin", (IElementHandler) (Attributes atts) -> {
            canvas.setLineJoin(atts.getValue("join"));
        });

        handlers.put("miterlimit", (IElementHandler) (Attributes atts) -> {
            canvas.setMiterLimit(Double.parseDouble(atts.getValue("limit")));
        });

        handlers.put("fontsize", (IElementHandler) (Attributes atts) -> {
            canvas.setFontSize(Double.parseDouble(atts.getValue("size")));
        });

        handlers.put("fontcolor", (IElementHandler) (Attributes atts) -> {
            canvas.setFontColor(atts.getValue("color"));
        });

        handlers.put("fontbackgroundcolor", (IElementHandler) (Attributes atts) -> {
            canvas.setFontBackgroundColor(atts.getValue("color"));
        });

        handlers.put("fontbordercolor", (IElementHandler) (Attributes atts) -> {
            canvas.setFontBorderColor(atts.getValue("color"));
        });

        handlers.put("fontfamily", (IElementHandler) (Attributes atts) -> {
            canvas.setFontFamily(atts.getValue("family"));
        });

        handlers.put("fontstyle", (IElementHandler) (Attributes atts) -> {
            canvas.setFontStyle(Integer.parseInt(atts.getValue("style")));
        });

        handlers.put("alpha", (IElementHandler) (Attributes atts) -> {
            canvas.setAlpha(Double.parseDouble(atts.getValue("alpha")));
        });

        handlers.put("fillalpha", (IElementHandler) (Attributes atts) -> {
            canvas.setFillAlpha(Double.parseDouble(atts.getValue("alpha")));
        });

        handlers.put("strokealpha", (IElementHandler) (Attributes atts) -> {
            canvas.setStrokeAlpha(Double.parseDouble(atts.getValue("alpha")));
        });

        handlers.put("fillcolor", (IElementHandler) (Attributes atts) -> {
            canvas.setFillColor(atts.getValue("color"));
        });

        handlers.put("shadowcolor", (IElementHandler) (Attributes atts) -> {
            canvas.setShadowColor(atts.getValue("color"));
        });

        handlers.put("shadowalpha", (IElementHandler) (Attributes atts) -> {
            canvas.setShadowAlpha(Double.parseDouble(atts.getValue("alpha")));
        });

        handlers.put("shadowoffset", (IElementHandler) (Attributes atts) -> {
            canvas.setShadowOffset(Double.parseDouble(atts.getValue("dx")),
                    Double.parseDouble(atts.getValue("dy")));
        });

        handlers.put("shadow", (IElementHandler) (Attributes atts) -> {
            canvas.setShadow(getValue(atts, "enabled", "1").equals("1"));
        });

        handlers.put("gradient", (IElementHandler) (Attributes atts) -> {
            canvas.setGradient(atts.getValue("c1"), atts.getValue("c2"),
                    Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")),
                    Double.parseDouble(atts.getValue("w")),
                    Double.parseDouble(atts.getValue("h")),
                    atts.getValue("direction"),
                    Double.parseDouble(getValue(atts, "alpha1", "1")),
                    Double.parseDouble(getValue(atts, "alpha2", "1")));
        });

        handlers.put("rect", (IElementHandler) (Attributes atts) -> {
            canvas.rect(Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")),
                    Double.parseDouble(atts.getValue("w")),
                    Double.parseDouble(atts.getValue("h")));
        });

        handlers.put("roundrect", (IElementHandler) (Attributes atts) -> {
            canvas.roundrect(Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")),
                    Double.parseDouble(atts.getValue("w")),
                    Double.parseDouble(atts.getValue("h")),
                    Double.parseDouble(atts.getValue("dx")),
                    Double.parseDouble(atts.getValue("dy")));
        });

        handlers.put("ellipse", (IElementHandler) (Attributes atts) -> {
            canvas.ellipse(Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")),
                    Double.parseDouble(atts.getValue("w")),
                    Double.parseDouble(atts.getValue("h")));
        });

        handlers.put("image", (IElementHandler) (Attributes atts) -> {
            canvas.image(Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")),
                    Double.parseDouble(atts.getValue("w")),
                    Double.parseDouble(atts.getValue("h")),
                    atts.getValue("src"),
                    atts.getValue("aspect").equals("1"),
                    atts.getValue("flipH").equals("1"),
                    atts.getValue("flipV").equals("1"));
        });

        handlers.put("text", (IElementHandler) (Attributes atts) -> {
            canvas.text(Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")),
                    Double.parseDouble(atts.getValue("w")),
                    Double.parseDouble(atts.getValue("h")),
                    atts.getValue("str"),
                    atts.getValue("align"),
                    atts.getValue("valign"),
                    getValue(atts, "wrap", "").equals("1"),
                    atts.getValue("format"),
                    atts.getValue("overflow"),
                    getValue(atts, "clip", "").equals("1"),
                    Double.parseDouble(getValue(atts, "rotation", "0")),
                    getValue(atts, "dir", null));
        });

        handlers.put("begin", (IElementHandler) (Attributes atts) -> {
            canvas.begin();
        });

        handlers.put("move", (IElementHandler) (Attributes atts) -> {
            canvas.moveTo(Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")));
        });

        handlers.put("line", (IElementHandler) (Attributes atts) -> {
            canvas.lineTo(Double.parseDouble(atts.getValue("x")),
                    Double.parseDouble(atts.getValue("y")));
        });

        handlers.put("quad", (IElementHandler) (Attributes atts) -> {
            canvas.quadTo(Double.parseDouble(atts.getValue("x1")),
                    Double.parseDouble(atts.getValue("y1")),
                    Double.parseDouble(atts.getValue("x2")),
                    Double.parseDouble(atts.getValue("y2")));
        });

        handlers.put("curve", (IElementHandler) (Attributes atts) -> {
            canvas.curveTo(Double.parseDouble(atts.getValue("x1")),
                    Double.parseDouble(atts.getValue("y1")),
                    Double.parseDouble(atts.getValue("x2")),
                    Double.parseDouble(atts.getValue("y2")),
                    Double.parseDouble(atts.getValue("x3")),
                    Double.parseDouble(atts.getValue("y3")));
        });

        handlers.put("close", (IElementHandler) (Attributes atts) -> {
            canvas.close();
        });

        handlers.put("stroke", (IElementHandler) (Attributes atts) -> {
            canvas.stroke();
        });

        handlers.put("fill", (IElementHandler) (Attributes atts) -> {
            canvas.fill();
        });

        handlers.put("fillstroke", (IElementHandler) (Attributes atts) -> {
            canvas.fillAndStroke();
        });
    }

    /**
     * Returns the given attribute value or an empty string.
     */
    protected String getValue(Attributes atts, String name, String defaultValue) {
        String value = atts.getValue(name);

        if (value == null) {
            value = defaultValue;
        }

        return value;
    }

    ;

	/**
	 * 
	 */
	protected interface IElementHandler {

        void parseElement(Attributes atts);
    }

}
