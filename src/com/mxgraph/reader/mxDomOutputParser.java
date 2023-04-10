package com.mxgraph.reader;

import java.util.Hashtable;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mxgraph.canvas.mxICanvas2D;

/**
 *
 * public static void main(String[] args) { try { String filename =
 * Test.class.getResource( "/com/mxgraph/online/exported.xml").getPath(); String
 * xml = mxUtils.readFile(filename); System.out.println("xml=" + xml);
 *
 * Document doc = mxUtils.parseXml(xml); Element root =
 * doc.getDocumentElement(); int width =
 * Integer.parseInt(root.getAttribute("width")); int height =
 * Integer.parseInt(root.getAttribute("height"));
 *
 * System.out.println("width=" + width + " height=" + height);
 *
 * BufferedImage img = mxUtils.createBufferedImage(width, height, Color.WHITE);
 * Graphics2D g2 = img.createGraphics(); mxUtils.setAntiAlias(g2, true, true);
 * mxDomOutputParser reader = new mxDomOutputParser( new
 * mxGraphicsExportCanvas(g2)); reader.read((Element)
 * root.getFirstChild().getNextSibling());
 *
 * ImageIO.write(img, "PNG", new File(
 * "C:\\Users\\Gaudenz\\Desktop\\test.png")); } catch (Exception e) {
 * e.printStackTrace(); } }
 *
 * // -------------
 *
 * Document doc = mxUtils.parseXml(xml); Element root =
 * doc.getDocumentElement(); mxDomOutputParser reader = new
 * mxDomOutputParser(canvas); reader.read(root.getFirstChild());
 */
public class mxDomOutputParser {

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
    public mxDomOutputParser(mxICanvas2D canvas) {
        this.canvas = canvas;
        initHandlers();
    }

    /**
     *
     */
    public void read(Node node) {
        while (node != null) {
            if (node instanceof Element elt) {
                IElementHandler handler = handlers.get(elt.getNodeName());

                if (handler != null) {
                    handler.parseElement(elt);
                }
            }

            node = node.getNextSibling();
        }
    }

    /**
     *
     */
    protected void initHandlers() {
        handlers.put("save", (IElementHandler) (Element elt) -> {
            canvas.save();
        });

        handlers.put("restore", (IElementHandler) (Element elt) -> {
            canvas.restore();
        });

        handlers.put("scale", (IElementHandler) (Element elt) -> {
            canvas.scale(Double.parseDouble(elt.getAttribute("scale")));
        });

        handlers.put("translate", (IElementHandler) (Element elt) -> {
            canvas.translate(Double.parseDouble(elt.getAttribute("dx")),
                    Double.parseDouble(elt.getAttribute("dy")));
        });

        handlers.put("rotate", (IElementHandler) (Element elt) -> {
            canvas.rotate(Double.parseDouble(elt.getAttribute("theta")),
                    elt.getAttribute("flipH").equals("1"), elt
                            .getAttribute("flipV").equals("1"), Double
                                    .parseDouble(elt.getAttribute("cx")), Double
                                            .parseDouble(elt.getAttribute("cy")));
        });

        handlers.put("strokewidth", (IElementHandler) (Element elt) -> {
            canvas.setStrokeWidth(Double.parseDouble(elt
                    .getAttribute("width")));
        });

        handlers.put("strokecolor", (IElementHandler) (Element elt) -> {
            canvas.setStrokeColor(elt.getAttribute("color"));
        });

        handlers.put("dashed", (IElementHandler) (Element elt) -> {
            String temp = elt.getAttribute("fixDash");
            boolean fixDash = temp != null && temp.equals("1");
            
            canvas.setDashed(elt.getAttribute("dashed").equals("1"), fixDash);
        });

        handlers.put("dashpattern", (IElementHandler) (Element elt) -> {
            canvas.setDashPattern(elt.getAttribute("pattern"));
        });

        handlers.put("linecap", (IElementHandler) (Element elt) -> {
            canvas.setLineCap(elt.getAttribute("cap"));
        });

        handlers.put("linejoin", (IElementHandler) (Element elt) -> {
            canvas.setLineJoin(elt.getAttribute("join"));
        });

        handlers.put("miterlimit", (IElementHandler) (Element elt) -> {
            canvas.setMiterLimit(Double.parseDouble(elt
                    .getAttribute("limit")));
        });

        handlers.put("fontsize", (IElementHandler) (Element elt) -> {
            canvas.setFontSize(Double.parseDouble(elt.getAttribute("size")));
        });

        handlers.put("fontcolor", (IElementHandler) (Element elt) -> {
            canvas.setFontColor(elt.getAttribute("color"));
        });

        handlers.put("fontbackgroundcolor", (IElementHandler) (Element elt) -> {
            canvas.setFontBackgroundColor(elt.getAttribute("color"));
        });

        handlers.put("fontbordercolor", (IElementHandler) (Element elt) -> {
            canvas.setFontBorderColor(elt.getAttribute("color"));
        });

        handlers.put("fontfamily", (IElementHandler) (Element elt) -> {
            canvas.setFontFamily(elt.getAttribute("family"));
        });

        handlers.put("fontstyle", (IElementHandler) (Element elt) -> {
            canvas.setFontStyle(Integer.parseInt(elt.getAttribute("style")));
        });

        handlers.put("alpha", (IElementHandler) (Element elt) -> {
            canvas.setAlpha(Double.parseDouble(elt.getAttribute("alpha")));
        });

        handlers.put("fillalpha", (IElementHandler) (Element elt) -> {
            canvas.setFillAlpha(Double.parseDouble(elt.getAttribute("alpha")));
        });

        handlers.put("strokealpha", (IElementHandler) (Element elt) -> {
            canvas.setStrokeAlpha(Double.parseDouble(elt.getAttribute("alpha")));
        });

        handlers.put("fillcolor", (IElementHandler) (Element elt) -> {
            canvas.setFillColor(elt.getAttribute("color"));
        });

        handlers.put("shadowcolor", (IElementHandler) (Element elt) -> {
            canvas.setShadowColor(elt.getAttribute("color"));
        });

        handlers.put("shadowalpha", (IElementHandler) (Element elt) -> {
            canvas.setShadowAlpha(Double.parseDouble(elt.getAttribute("alpha")));
        });

        handlers.put("shadowoffset", (IElementHandler) (Element elt) -> {
            canvas.setShadowOffset(Double.parseDouble(elt.getAttribute("dx")),
                    Double.parseDouble(elt.getAttribute("dy")));
        });

        handlers.put("shadow", (IElementHandler) (Element elt) -> {
            canvas.setShadow(elt.getAttribute("enabled").equals("1"));
        });

        handlers.put("gradient", (IElementHandler) (Element elt) -> {
            canvas.setGradient(elt.getAttribute("c1"),
                    elt.getAttribute("c2"),
                    Double.parseDouble(elt.getAttribute("x")),
                    Double.parseDouble(elt.getAttribute("y")),
                    Double.parseDouble(elt.getAttribute("w")),
                    Double.parseDouble(elt.getAttribute("h")),
                    elt.getAttribute("direction"),
                    Double.parseDouble(getValue(elt, "alpha1", "1")),
                    Double.parseDouble(getValue(elt, "alpha2", "1")));
        });

        handlers.put("rect", (IElementHandler) (Element elt) -> {
            canvas.rect(Double.parseDouble(elt.getAttribute("x")),
                    Double.parseDouble(elt.getAttribute("y")),
                    Double.parseDouble(elt.getAttribute("w")),
                    Double.parseDouble(elt.getAttribute("h")));
        });

        handlers.put("roundrect", (IElementHandler) (Element elt) -> {
            canvas.roundrect(Double.parseDouble(elt.getAttribute("x")),
                    Double.parseDouble(elt.getAttribute("y")),
                    Double.parseDouble(elt.getAttribute("w")),
                    Double.parseDouble(elt.getAttribute("h")),
                    Double.parseDouble(elt.getAttribute("dx")),
                    Double.parseDouble(elt.getAttribute("dy")));
        });

        handlers.put("ellipse", (IElementHandler) (Element elt) -> {
            canvas.ellipse(Double.parseDouble(elt.getAttribute("x")),
                    Double.parseDouble(elt.getAttribute("y")),
                    Double.parseDouble(elt.getAttribute("w")),
                    Double.parseDouble(elt.getAttribute("h")));
        });

        handlers.put("image", (IElementHandler) (Element elt) -> {
            canvas.image(Double.parseDouble(elt.getAttribute("x")), Double
                    .parseDouble(elt.getAttribute("y")), Double
                            .parseDouble(elt.getAttribute("w")), Double
                                    .parseDouble(elt.getAttribute("h")), elt
                                            .getAttribute("src"), elt.getAttribute("aspect")
                                                    .equals("1"), elt.getAttribute("flipH").equals("1"),
                                                    elt.getAttribute("flipV").equals("1"));
        });

        handlers.put("text", (IElementHandler) (Element elt) -> {
            canvas.text(Double.parseDouble(elt.getAttribute("x")),
                    Double.parseDouble(elt.getAttribute("y")),
                    Double.parseDouble(elt.getAttribute("w")),
                    Double.parseDouble(elt.getAttribute("h")),
                    elt.getAttribute("str"),
                    elt.getAttribute("align"),
                    elt.getAttribute("valign"),
                    getValue(elt, "wrap", "").equals("1"),
                    elt.getAttribute("format"),
                    elt.getAttribute("overflow"),
                    getValue(elt, "clip", "").equals("1"),
                    Double.parseDouble(getValue(elt, "rotation", "0")),
                    elt.getAttribute("dir"));
        });

        handlers.put("begin", (IElementHandler) (Element elt) -> {
            canvas.begin();
        });

        handlers.put("move", (IElementHandler) (Element elt) -> {
            canvas.moveTo(Double.parseDouble(elt.getAttribute("x")),
                    Double.parseDouble(elt.getAttribute("y")));
        });

        handlers.put("line", (IElementHandler) (Element elt) -> {
            canvas.lineTo(Double.parseDouble(elt.getAttribute("x")),
                    Double.parseDouble(elt.getAttribute("y")));
        });

        handlers.put("quad", (IElementHandler) (Element elt) -> {
            canvas.quadTo(Double.parseDouble(elt.getAttribute("x1")),
                    Double.parseDouble(elt.getAttribute("y1")),
                    Double.parseDouble(elt.getAttribute("x2")),
                    Double.parseDouble(elt.getAttribute("y2")));
        });

        handlers.put("curve", (IElementHandler) (Element elt) -> {
            canvas.curveTo(Double.parseDouble(elt.getAttribute("x1")),
                    Double.parseDouble(elt.getAttribute("y1")),
                    Double.parseDouble(elt.getAttribute("x2")),
                    Double.parseDouble(elt.getAttribute("y2")),
                    Double.parseDouble(elt.getAttribute("x3")),
                    Double.parseDouble(elt.getAttribute("y3")));
        });

        handlers.put("close", (IElementHandler) (Element elt) -> {
            canvas.close();
        });

        handlers.put("stroke", (IElementHandler) (Element elt) -> {
            canvas.stroke();
        });

        handlers.put("fill", (IElementHandler) (Element elt) -> {
            canvas.fill();
        });

        handlers.put("fillstroke", (IElementHandler) (Element elt) -> {
            canvas.fillAndStroke();
        });
    }

    /**
     * Returns the given attribute value or an empty string.
     */
    protected String getValue(Element elt, String name, String defaultValue) {
        String value = elt.getAttribute(name);

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

        void parseElement(Element elt);
    }

}
