package com.heisenberg.asphodel;

import static com.heisenberg.asphodel.RenderConstants.NUM_VERTICES;

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
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Xml;

/**
 * Stores 3D information about actors in the game
 * 
 * @author Ali
 *
 */
public class Mesh {
    // Used as temporary storage during DAE parsing
    private class GeomEntry {
        public String id;
        public short[] sdata;
        public float[] fdata;
        public String type;
    }
    
    // Sub meshes
    ArrayList<SubMesh> subMeshes;
    
    // Corresponding Resource ID
    private int meshID;
    
    // Constructor calls file load
    public Mesh(int resID) {
        subMeshes = new ArrayList<SubMesh>();
        meshID = resID;
        
        if (resID != -3) {
            System.out.println("Trying to load DAE");
            loadDAERaw();
        }
        else {
            System.out.println("Triangle time");
            
            SubMesh sm = new SubMesh();
            subMeshes.add(sm);
            float[] coords = { 0, -1, 0, 1, 1, 0, -1, 1, 0 };
            ByteBuffer bb = ByteBuffer.allocateDirect(4* coords.length);
            bb.order(ByteOrder.nativeOrder());
            FloatBuffer fb = bb.asFloatBuffer();
            fb.put(coords);
            sm.setVertexBuffer(fb);
        }
        
        GameData.addMesh(this);
    }
    
    // Rendering code
    public void draw(DrawHelper dh) {
        // Just loop the submeshes for now
        for (SubMesh sm : subMeshes) {
            sm.draw(dh);
        }
    }
    
    // File loader
    private void loadDAERaw() {
        // Setup to read from the .DAE file (type of XML)
        InputStream is = null;
        try {
            MyActivity act = MyActivity.curActivity;
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
            
            // Loop to create submeshes
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                // Go straight to geometry
                if (!xmlSkipTo(parser, "mesh")) {
                    // Couldn't find it
                    break;
                }
                SubMesh curSubMesh = new SubMesh();
                subMeshes.add(curSubMesh);
                
                String posid, normid;
                posid = null;
                normid = null;
                
                // Loop SHOULD terminate before EOF... but just in case
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    // Exit loop at end of mesh decl
                    if (parser.getEventType() == XmlPullParser.END_TAG) {
                        String name = parser.getName();
                        if (name.equals("mesh")) {
                            break;
                        }
                    }
                    // Otherwise skip eveything but the start tags
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
                                            
                        ge.type = "float";
                        
                        geom.add(ge);
                    }
                    // Triangles: Index buffer
                    else if (name.equals("triangles")) {
                        GeomEntry ge = new GeomEntry();
                        
                        // Skip to index buffer description
                        xmlSkipTo(parser, "input");
                        ge.id = parser.getAttributeValue(2);
                        
                        // Skip to index buffer content
                        xmlSkipTo(parser, "p");
                        parser.next();
                        
                        // Retrieve and parse text as shorts
                        String raw = parser.getText();
                        String[] values = raw.split(" ");
                        ge.sdata = new short[values.length];
                        
                        for (int i = 0; i < values.length; i++) {
                            ge.sdata[i] = Short.parseShort(values[i]);
                        }
                        
                        // Store count & type
                        curSubMesh.setIndexCount(ge.sdata.length);
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
                
                // Use the data (store in current sub-mesh)
                for (GeomEntry ge : geom) {
                    if (ge.type.equals("float")) {
                        // Get as a buffer
                        ByteBuffer bb = ByteBuffer.allocateDirect(ge.fdata.length * 4);
                        bb.order(ByteOrder.nativeOrder());
                        FloatBuffer fb = bb.asFloatBuffer();
                        fb.put(ge.fdata);
                        
                        if (posid.equals(ge.id)) {
                            // Save vertex buffer
                            curSubMesh.setVertexBuffer(fb);
                        }
                        else if (normid.equals(ge.id)) {
                            // Save normal buffer
                            curSubMesh.setNormalBuffer(fb);
                        }
                    }
                    else if (ge.type.equals("short")) {
                        ByteBuffer bb = ByteBuffer.allocateDirect(ge.sdata.length * 2);
                        bb.order(ByteOrder.nativeOrder());
                        ShortBuffer sb = bb.asShortBuffer();
                        sb.put(ge.sdata);
                        
                        curSubMesh.setIndexBuffer(sb);
                    }
                }
            }
        }
        catch (Exception e) {
            System.err.println("Unable to read DAE! "+e.getMessage());
            return;
        }
        
        // Success!
        System.out.println("DAE successfully loaded");
    }
    
    private boolean xmlSkipTo(XmlPullParser parser, String name) throws XmlPullParserException, IOException {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            
            String n = parser.getName();
            
            if (n.equals(name)) {
                //System.out.println("Found "+name);
                return true;
            }
        }
        
        return false;
    }
    
    private void xmlSkipToEnd(XmlPullParser parser, String name) throws XmlPullParserException, IOException {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.END_TAG) {
                continue;
            }
            
            String n = parser.getName();
            
            if (n.equals(name)) {
                System.out.println("End of "+name);
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
