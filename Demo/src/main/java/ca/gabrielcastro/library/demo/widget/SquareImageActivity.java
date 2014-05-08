package ca.gabrielcastro.library.demo.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ca.gabrielcastro.library.demo.app.BaseActivity;
import ca.gabrielcastro.library.widget.SquareImageView;

public class SquareImageActivity extends BaseActivity {

    public static final int IMAGE_ID = android.R.id.primary;
    private View square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        layout.setBackgroundColor(Color.BLACK);

        SquareImageView imageView = new SquareImageView(this);
        imageView.setBackgroundColor(Color.WHITE);
        imageView.setId(IMAGE_ID);

        FrameLayout.LayoutParams matchParent = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.addView(imageView, matchParent);

        setContentView(layout);

        square = imageView;
    }
}
