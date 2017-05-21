package Editor;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextField;

import SKEngine.Core.Sprite;

public class EditorToolkit extends JPanel {
    public static String currentTool = "BRUSH";
    public static Sprite currentSprite = null;
    public static String currentSpriteName = null;
    public Sprite previousSprite;
    public String prevSpriteName;

    public EditorToolkit() {
        super(new GridLayout(3,4));
        JTextField textField = new JTextField();
        JButton brushTool = createButton("Brush", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentTool.equals("BRUSH"))
                    return;
                    
                currentTool = "BRUSH";
            }
        });
        JButton eraserTool = createButton("Eraser", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentTool = "ERASER";
            }
        });
        JButton colliderTool = createButton("Collider", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentTool.equals("COLLIDER"))
                    return;
                currentTool = "COLLIDER";
            }
        });
        JButton rbodyTool = createButton("RBody Tool", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentTool.equals("RBODY"))
                    return;
                currentTool = "RBODY";
            }
        });
        JButton export = createButton("Export", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(textField.getText() != null) {
                    Exporter.export(Viewport.objects, textField.getText());
                }
            }
        });
        JButton load = createButton("Load", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(textField.getText() != null) {
                    Exporter.load("map.sk");
                }
            }
        });
        JButton selectionTool = createButton("Selection", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentTool.equals("SELECTION"))
                    return;
                currentTool = "SELECTION";
            }
        });

        this.add(brushTool);
        this.add(eraserTool);
        this.add(colliderTool);
        this.add(rbodyTool);
        this.add(selectionTool);
        

        this.add(export);
        this.add(load);
        this.add(textField);
        initialize();
    }

    public void initialize() {
        JFrame frame = new JFrame();

        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JButton createButton(String label, ActionListener callback) {
        JButton button = new JButton();
        button.setLabel(label);
        button.addActionListener(callback);
        return button;
    }

    public static void main(String[] args) {
        new EditorToolkit();
    }
}