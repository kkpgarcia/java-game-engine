package Editor;

import java.io.File;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import SKEngine.Core.Sprite;
import SKEngine.Collections.Dictionary;
import SKEngine.Utility.Resources;

public class AssetWindow extends JPanel {
    public static Sprite currentSelection = null;
    private Dictionary<String, Sprite> assetsDictionary;
    private ArrayList<String> assetNames = new ArrayList<String>();

    public AssetWindow() {
        assetsDictionary = new Dictionary<String, Sprite>();
        initialize();
    }

    public void initialize() {
        getDirectoryFiles();
        JList list = new JList(assetNames.toArray());
        list.setCellRenderer(new AssetWindowRenderer());

        JScrollPane scroll  = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension (300, 400));

        JFrame frame = new JFrame();
        frame.add(scroll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void getDirectoryFiles() {
        assetNames = Resources.getFileNamesFromAssets();

        for(String s : assetNames) {
            BufferedImage image = null;
            
            try {
                image = Resources.loadImageFull(s);
            } catch(Exception e) {
                System.out.println(s);
            }
            if(image == null) 
                continue;

            try {
                assetsDictionary.add(s, new Sprite(image));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void onSelected(String selection) {
        EditorToolkit.currentSprite = assetsDictionary.getValue(selection);
        EditorToolkit.currentSpriteName = selection;
    }

    public class AssetWindowRenderer extends DefaultListCellRenderer {
        Font font = new Font("helvetica", Font.PLAIN, 12);

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon(new ImageIcon(assetsDictionary.getValue((String)value).image));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            if(isSelected) {
                onSelected((String)value);
            }
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AssetWindow();
            }
        });
    }
}