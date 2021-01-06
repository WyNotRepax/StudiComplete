package de.hsos.prog3.bsteinka.ab07.swingolfsheet.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class SynchronizedScrollView extends ScrollView {
    private SynchronizedScrollView sync;
    private boolean isSyncScroll = false;

    public SynchronizedScrollView(Context context) {
        super(context);
    }

    public SynchronizedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SynchronizedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSync(SynchronizedScrollView sync) {
        this.sync = sync;
        sync.sync = this;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (sync != null && !isSyncScroll) {
            sync.syncScrollTo(l, t);
        }
    }

    private void syncScrollTo(int x, int y) {
        isSyncScroll = true;
        scrollTo(x, y);
    }
}
