package unknown.logica;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

//http://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
public abstract class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private enum Position{
        EMPTY, UP, RIGHT, DOWN, LEFT
    }
    private Position position = Position.EMPTY;

    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            switch (position){
                case EMPTY:
                    return false;

                case UP:
                    onSwipeTopUp();
                    break;

                case RIGHT:
                    onSwipeRightUp();
                    break;

                case DOWN:
                    onSwipeBottomUp();
                    break;

                case LEFT:
                    onSwipeLeftUp();
                    break;
            }
            position = Position.EMPTY;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRightHold();
                        } else {
                            onSwipeLeftHold();
                        }
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottomHold();
                    } else {
                        onSwipeTopHold();
                    }
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }
    }

    public void onSwipeRightHold(){
        position = Position.RIGHT;
    }

    public void onSwipeLeftHold(){
        position = Position.LEFT;
    }

    public void onSwipeTopHold(){
        position = Position.UP;
    }

    public void onSwipeBottomHold(){
        position = Position.DOWN;
    }

    public abstract void onSwipeRightUp();

    public abstract void onSwipeLeftUp();

    public abstract void onSwipeTopUp();

    public abstract void onSwipeBottomUp();
}