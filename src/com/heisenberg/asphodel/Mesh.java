package com.heisenberg.asphodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources.NotFoundException;
import android.util.Xml;

/**
 * Stores 3D information about actors in the game
 * 
 * @author Ali
 *
 */
public class Mesh {
    private class GeomEntry {
        public String id;
        public short[] sdata;
        public float[] fdata;
        public String type;
    }
    
    // Raw data
    private float verts[];
    private float normals[];
    private float indices[];
    
    // Buffers
    private FloatBuffer vb;
    private FloatBuffer nb;
    private ShortBuffer ib;
    
    // Corresponding Resource ID
    private int meshID;
    
    // Constructor calls file load
    public Mesh(int resID) {
        meshID = resID;
        System.out.println("Trying to load DAE");
        loadDAERaw();
    }
    
    // Rendering code
    public void draw() {
        
    }
    
    // File loader
    private void loadDAERaw() {
        // Setup to read from the .DAE file (type of XML)
        InputStream is = null;
        try {
            MyActivity act = MyActivity.getInstance();
            is = act.getResources().openRawResource(meshID);
        }
        catch (NotFoundException e) {
            System.out.println("Error while attempting to open resource. " + e.getMessage());
            return;
        }
        
        // Raw data
        ArrayList<GeomEntry> geom = new ArrayList<GeomEntry>();
        
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            parser.nextTag();
            
            // Needs a valid DAE file
            parser.require(XmlPullParser.START_TAG, null, "COLLADA");
            
            // Go straight to geometry
            xmlSkipTo(parser, "mesh");
            
            String posid, normid;
            posid = null;
            normid = null;
            
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                
                String name = parser.getName();
                // Source: Floating-point data
                if (name.equals("source")) {
                    GeomEntry ge = new GeomEntry();
                    ge.id = parser.getAttributeValue(0);
                    
                    // Skip to the data
                    xmlSkipTo(parser, "float_array");
                    parser.next();
                    String raw = parser.getText();
                    String[] values = raw.split(" ");
                    ge.fdata = new float[values.length];
                    
                    for (int i = 0; i < values.length; i++) {
                        ge.fdata[i] = Float.parseFloat(values[i]);
                    }
                    
                    System.out.println("Read: "+ge.fdata.length+" floats");
                    
                    ge.type = "float";
                    
                    geom.add(ge);
                }
                // Triangles: Index buffer
                else if (name.equals("triangles")) {
                    GeomEntry ge = new GeomEntry();
                    xmlSkipTo(parser, "input");
                    ge.id = parser.getAttributeValue(2);
                    xmlSkipTo(parser, "p");
                    parser.next();
                    String raw = parser.getText();
                    String[] values = raw.split(" ");
                    ge.sdata = new short[values.length];
                    
                    for (int i = 0; i < values.length; i++) {
                        ge.sdata[i] = Short.parseShort(values[i]);
                    }
                    
                    ge.type = "short";
                    
                    geom.add(ge);
                }
                // Vertices: Determines usage
                else if (name.equals("vertices")) {
                    xmlSkipTo(parser, "input");
                    if (parser.getAttributeValue(0).equals("POSITION")) {
                        posid = parser.getAttributeValue(1).substring(1);
                    }
                    xmlSkipTo(parser, "input");
                    if (parser.getAttributeValue(0).equals("NORMAL")) {
                        normid = parser.getAttributeValue(1).substring(1);
                    }
                }
            }
            
            // Use the data
            for (GeomEntry ge : geom) {
                if (ge.type.equals("float")) {
                    // Get as a buffer
                    ByteBuffer bb = ByteBuffer.allocateDirect(ge.fdata.length * 4);
                    bb.order(ByteOrder.nativeOrder());
                    FloatBuffer fb = bb.asFloatBuffer();
                    fb.put(ge.fdata);
                    
                    if (posid.equals(ge.id)) {
                        // Save vertex buffer
                        vb = fb;
                    }
                    else if (normid.equals(ge.id)) {
                        // Save normal buffer
                        nb = fb;
                    }
                }
                else if (ge.type.equals("short")) {
                    ByteBuffer bb = ByteBuffer.allocateDirect(ge.sdata.length * 2);
                    bb.order(ByteOrder.nativeOrder());
                    ShortBuffer sb = bb.asShortBuffer();
                    sb.put(ge.sdata);
                    
                    ib = sb;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Unable to read DAE! "+e.getMessage());
            return;
        }
        
        // Success!
        System.out.println("Done with DAE!");
    }
    
    private void xmlSkipTo(XmlPullParser parser, String name) throws XmlPullParserException, IOException {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            
            String n = parser.getName();
            
            if (n.equals(name)) {
                System.out.println("Found "+name);
                return;
            }
        }
    }
    
    private void xmlSkip(XmlPullParser parser) throws XmlPullParserException, IOException {
        int depth = 1;
        
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.START_TAG:
                depth++;
                break;
            case XmlPullParser.END_TAG:
                depth--;
                break;
            }
        }
    }
}
