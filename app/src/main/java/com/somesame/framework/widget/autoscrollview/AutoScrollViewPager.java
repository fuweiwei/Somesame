package com.somesame.framework.widget.autoscrollview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

/**
 * Auto Scroll View Pager
 * <ul>
 * <strong>Basic Setting and Usage</strong>
 * <li>{@link #startAutoScroll()} start auto scroll, or
 * {@link #startAutoScroll(int)} start auto scroll delayed</li>
 * <li>{@link #stopAutoScroll()} stop auto scroll</li>
 * <li>{@link #setInterval(long)} set auto scroll time in milliseconds, default
 * is {@link #DEFAULT_INTERVAL}</li>
 * </ul>
 * <ul>
 * <strong>Advanced Settings and Usage</strong>
 * <li>{@link #setDirection(int)} set auto scroll direction</li>
 * <li>{@link #setCycle(boolean)} set whether automatic cycle when auto scroll
 * reaching the last or first item, default is true</li>
 * <li>{@link #setSlideBorderMode(int)} set how to process when sliding at the
 * last or first item</li>
 * <li>{@link #setStopScrollWhenTouch(boolean)} set whether stop auto scroll
 * when touching, default is true</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-30
 */
public class AutoScrollViewPager extends ViewPager {

    public static final int DEFAULT_INTERVAL = 1500;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    /** do nothing when sliding at the last or first item **/
    public static final int SLIDE_BORDER_MODE_NONE = 0;
    /** cycle when sliding at the last or first item **/
    public static final int SLIDE_BORDER_MODE_CYCLE = 1;
    /** deliver event to parent when sliding at the last or first item **/
    public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;

    /** auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} **/
    private long interval = DEFAULT_INTERVAL;
    /** auto scroll direction, default is {@link #RIGHT} **/
    private int direction = RIGHT;
    /**
     * whether automatic cycle when auto scroll reaching the last or first item,
     * default is true
     **/
    private boolean isCycle = true;
    /** whether stop auto scroll when touching, default is true **/
    private boolean stopScrollWhenTouch = true;
    /**
     * how to process when sliding at the last or first item, default is
     * {@link #SLIDE_BORDER_MODE_NONE}
     **/
    private int slideBorderMode = SLIDE_BORDER_MODE_NONE;
    /** whether animating when auto scroll at the last or first item **/
    private boolean isBorderAnimation = true;
    /** scroll factor for auto scroll animation, default is 1.0 **/
    private double autoScrollFactor = 1.0;
    /** scroll factor for swipe scroll animation, default is 1.0 **/
    private double swipeScrollFactor = 1.0;

    private Handler handler;
    private boolean isAutoScroll = false;
    private boolean isStopByTouch = false;
    private float touchX = 0f, downX = 0f;
    private CustomDurationScroller scroller = null;

    public static final int SCROLL_WHAT = 0;

    public AutoScrollViewPager(Context paramContext) {
        super(paramContext);
        init();
    }

    public AutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        handler = new MyHandler();
        setViewPagerScroller();
    }

    /**
     * start auto scroll, first scroll delay time is {@link #getInterval()}
     */
    public void startAutoScroll() {
        isAutoScroll = true;
        sendScrollMessage((long) (interval));
        // sendScrollMessage((long) (interval + scroller.getDuration()/
        // autoScrollFactor * swipeScrollFactor));
    }

    /**
     * start auto scroll
     * 
     * @param delayTimeInMills
     *            first scroll delay time
     */
    public void startAutoScroll(int delayTimeInMills) {
        isAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }

    /**
     * set the factor by which the duration of sliding animation will change
     * while swiping
     */
    public void setSwipeScrollDurationFactor(double scrollFactor) {
        swipeScrollFactor = scrollFactor;
    }

    /**
     * set the factor by which the duration of sliding animation will change
     * while auto scrolling
     */
    public void setAutoScrollDurationFactor(double scrollFactor) {
        autoScrollFactor = scrollFactor;
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    /**
     * set ViewPager scroller to change animation duration when sliding
     */
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new CustomDurationScroller(getContext(),
                    (Interpolator) interpolatorField.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * scroll only once
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;
        if (nextItem < 0) {
            if (isCycle) {
                setCurrentItem(totalCount - 1, isBorderAnimation);
            }
        } else if (nextItem == totalCount) {
            if (isCycle) {
                setCurrentItem(0, isBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    /**
     * <ul>
     * if stopScrollWhenTouch is true
     * <li>if event is down, stop auto scroll.</li>
     * <li>if event is up, start auto scroll again.</li>
     * </ul>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = MotionEventCompat.getActionMasked(ev);

        if (stopScrollWhenTouch) {
            if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
                isStopByTouch = true;
                stopAutoScroll();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                startAutoScroll();
            }
        }

        if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT
                || slideBorderMode == SLIDE_BORDER_MODE_CYCLE) {
            touchX = ev.getX();
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                downX = touchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int pageCount = adapter == null ? 0 : adapter.getCount();
            /**
             * current index is first one and slide to right or current index is
             * last one and slide to left.<br/>
             * if slide border mode is to parent, then
             * requestDisallowInterceptTouchEvent false.<br/>
             * else scroll to last one when current item is first one, scroll to
             * first one when current item is last one.
             */

            if ((currentItem == 0 && downX <= touchX)
                    || (currentItem == pageCount - 1 && downX >= touchX)) {
                if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (pageCount > 1) {
                        setCurrentItem(pageCount - currentItem - 1, isBorderAnimation);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                } return false;
//                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
            case SCROLL_WHAT:
                scroller.setScrollDurationFactor(autoScrollFactor);
                scrollOnce();
                scroller.setScrollDurationFactor(swipeScrollFactor);
                sendScrollMessage(interval);
                // sendScrollMessage(interval + scroller.getDuration());
            default:
                break;
            }
        }
    }

    /**
     * get auto scroll time in milliseconds, default is
     * {@link #DEFAULT_INTERVAL}
     * 
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * set auto scroll time in milliseconds, default is
     * {@link #DEFAULT_INTERVAL}
     * 
     * @param interval
     *            the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * get auto scroll direction
     * 
     * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    /**
     * set auto scroll direction
     * 
     * @param direction
     *            {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * whether automatic cycle when auto scroll reaching the last or first item,
     * default is true
     * 
     * @return the isCycle
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * set whether automatic cycle when auto scroll reaching the last or first
     * item, default is true
     * 
     * @param isCycle
     *            the isCycle to set
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * whether stop auto scroll when touching, default is true
     * 
     * @return the stopScrollWhenTouch
     */
    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    /**
     * set whether stop auto scroll when touching, default is true
     * 
     * @param stopScrollWhenTouch
     */
    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    /**
     * get how to process when sliding at the last or first item
     * 
     * @return the slideBorderMode {@link #SLIDE_BORDER_MODE_NONE},
     *         {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *         {@link #SLIDE_BORDER_MODE_CYCLE}, default is
     *         {@link #SLIDE_BORDER_MODE_NONE}
     */
    public int getSlideBorderMode() {
        return slideBorderMode;
    }

    /**
     * set how to process when sliding at the last or first item
     * 
     * @param slideBorderMode
     *            {@link #SLIDE_BORDER_MODE_NONE},
     *            {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *            {@link #SLIDE_BORDER_MODE_CYCLE}, default is
     *            {@link #SLIDE_BORDER_MODE_NONE}
     */
    public void setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
    }

    /**
     * whether animating when auto scroll at the last or first item, default is
     * true
     * 
     * @return
     */
    public boolean isBorderAnimation() {
        return isBorderAnimation;
    }

    /**
     * set whether animating when auto scroll at the last or first item, default
     * is true
     * 
     * @param isBorderAnimation
     */
    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return gestureDetector.onTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return gestureDetector.onTouchEvent(ev);
//    }
//
//    private GestureDetector gestureDetector= new GestureDetector(new GestureDetector.OnGestureListener()
//    {
//
//        // 鼠标按下的时候，会产生onDown。由一个ACTION_DOWN产生。
//        public boolean onDown(MotionEvent event) {
//
//            //DisplayEventType("mouse down" + " " + event.getX() + "," + event.getY());
//            return false;
//        }
//        // 用户按下触摸屏、快速移动后松开,这个时候，你的手指运动是有加速度的。
//        // 由1个MotionEvent ACTION_DOWN,
//        // 多个ACTION_MOVE, 1个ACTION_UP触发
//        // e1：第1个ACTION_DOWN MotionEvent
//        // e2：最后一个ACTION_MOVE MotionEvent
//        // velocityX：X轴上的移动速度，像素/秒
//        // velocityY：Y轴上的移动速度，像素/秒
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                               float velocityY) {
//            //DisplayEventType("onFling");
//
//            float x1 = e1.getX();
//            float x2 = e2.getX();
//            if (Math.abs(x2 -x1) > 390){
//                return false;
//            }
//            return true;
//        }
//        // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
//        public void onLongPress(MotionEvent event) {
//            //DisplayEventType("on long pressed");
//        }
//        // 滚动事件，当在触摸屏上迅速的移动，会产生onScroll。由ACTION_MOVE产生
//        // e1：第1个ACTION_DOWN MotionEvent
//        // e2：最后一个ACTION_MOVE MotionEvent
//        // distanceX：距离上次产生onScroll事件后，X抽移动的距离
//        // distanceY：距离上次产生onScroll事件后，Y抽移动的距离
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//                                float distanceY) {
//            //DisplayEventType("onScroll" + " " + distanceX + "," + distanceY);
//
//            return false;
//        }
//        //点击了触摸屏，但是没有移动和弹起的动作。onShowPress和onDown的区别在于
//        //onDown是，一旦触摸屏按下，就马上产生onDown事件，但是onShowPress是onDown事件产生后，
//        //一段时间内，如果没有移动鼠标和弹起事件，就认为是onShowPress事件。
//        public void onShowPress(MotionEvent event) {
//            //DisplayEventType("pressed");
//
//        }
//        // 轻击触摸屏后，弹起。如果这个过程中产生了onLongPress、onScroll和onFling事件，就不会
//        // 产生onSingleTapUp事件。
//        public boolean onSingleTapUp(MotionEvent event) {
//            //DisplayEventType("Tap up");
//            return false;
//        }
//
//    });
}
