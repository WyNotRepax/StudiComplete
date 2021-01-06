package de.hsos.prog3.bsteinka.ab07.swingolfsheet.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class SynchronizedHorizontalScrollView extends HorizontalScrollView {
    private boolean isSyncScroll = false;
    private SynchronizedHorizontalScrollView sync;

    public SynchronizedHorizontalScrollView(Context context) {
        super(context);
    }

    public SynchronizedHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SynchronizedHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSync(SynchronizedHorizontalScrollView sync) {
        this.sync = sync;
        sync.sync = this;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (sync != null && !isSyncScroll) {
            sync.syncScrollTo(l, t);
        }
        isSyncScroll = false;
    }

    private void syncScrollTo(int x, int y) {
        isSyncScroll = true;
        scrollTo(x, y);
    }


}
