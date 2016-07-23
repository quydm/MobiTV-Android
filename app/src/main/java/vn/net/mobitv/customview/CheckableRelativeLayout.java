package vn.net.mobitv.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CheckableRelativeLayout extends LinearLayout implements Checkable {

	private boolean isChecked;
	private List<Checkable> checkableViews;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialise(attrs);
	}

	public CheckableRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialise(attrs);
	}

	public CheckableRelativeLayout(Context context, int checkableId) {
		super(context);
		initialise(null);
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		for (Checkable c : checkableViews)
			c.setChecked(isChecked);
	}

	public void toggle() {
		isChecked = !isChecked;
		for (Checkable c : checkableViews)
			c.toggle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		int childCount = getChildCount();
		for (int i = 0; i < childCount; ++i)
			findCheckableChildren(getChildAt(i));
	}

	private void initialise(AttributeSet attrs) {
		isChecked = false;
		checkableViews = new ArrayList<Checkable>(5);
	}

	private void findCheckableChildren(View v) {
		if (v instanceof Checkable)
			checkableViews.add((Checkable) v);

		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			int childCount = vg.getChildCount();
			for (int i = 0; i < childCount; ++i)
				findCheckableChildren(vg.getChildAt(i));
		}
	}

}
