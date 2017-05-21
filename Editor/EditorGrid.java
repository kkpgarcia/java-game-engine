package Editor;

import java.util.ArrayList;
import SKEngine.Core.Vector2;

public class EditorGrid {
    int multiplier = 2;
    public static ArrayList<EditorCellGrid> cellGrid = new ArrayList<EditorCellGrid>();
    public EditorGrid(int cellSize, int width, int height) {
        int widthCount = (width/cellSize) * multiplier;
        int heightCount = (height/cellSize) * multiplier;
        for(int i = 0; i < widthCount; i++) {
            for(int j = 0; j < heightCount; j++) {
                EditorCellGrid cell = new EditorCellGrid(cellSize, new Vector2(-width/2 + (i * cellSize),-height/2 + (j * cellSize)));
                cellGrid.add(cell);
            }
        }
    }
}