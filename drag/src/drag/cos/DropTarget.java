package drag.cos;

import android.graphics.Rect;

public interface DropTarget {
    void onDragEnter(Rect rect, DragInfo info);
    void onDragOver(Rect rect, DragInfo info);
    void onDragExit(DragInfo info);

    boolean acceptDrop(Rect rect, DragInfo info);
    void onDrop(Rect rect, DragInfo info);

    void getLocationOnScreen(int[] loc);
}