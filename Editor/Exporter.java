package Editor;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import java.util.StringTokenizer;

import SKEngine.Utility.Resources;
import SKEngine.Core.Sprite;
import SKEngine.Core.Vector2;

public class Exporter {
    public static void export(ArrayList<EditorObject> object, String fileName) {
        StringBuilder data = new StringBuilder();// = serializeObject(object);

        for(EditorObject obj : object) {
            String serializedObject = serializeObject(obj);
            data.append(serializedObject);
        }

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            bw.write(data.toString());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null)
                    bw.close();
                if(fw != null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String serializeObject(EditorObject object) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(object.transform.position.x)).append(" ");
        builder.append(String.valueOf(object.transform.position.y)).append(" ");
        builder.append(String.valueOf(object.transform.scale.x)).append(" ");
        builder.append(String.valueOf(object.transform.scale.y)).append(" ");
        builder.append(String.valueOf(object.spriteName)).append("\n");
        return builder.toString();
    }

    public static void load(String fileName) {
        File file = new File(fileName);
        BufferedReader br = null;
        
        try {
            String line;
            br = new BufferedReader(new FileReader(file));

            while((line = br.readLine()) != null) {
                EditorObject editorObject = deserializeObject(line);
            } 
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /*public static void import(String fileName) {
        
    }*/

    private static EditorObject deserializeObject(String data) {
        StringTokenizer st = new StringTokenizer(data);
        EditorObject object = new EditorObject();
        object.transform.position = new Vector2(Float.parseFloat(st.nextToken()),Float.parseFloat(st.nextToken()));
        object.transform.scale = new Vector2(Float.parseFloat(st.nextToken()),Float.parseFloat(st.nextToken()));

        BufferedImage image = Resources.loadImageFull(st.nextToken());
        object.renderer.sprite = new Sprite(image);
        return object;
    }
}