package ca.gabrielcastro.library.demo.widget;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

public class TestSquareImage extends ActivityInstrumentationTestCase2<SquareImageActivity> {

    public TestSquareImage() {
        super(SquareImageActivity.class);
    }

    private View getImageViewOnActivity() {
        Activity activity = getActivity();
        getInstrumentation().waitForIdleSync();
        return activity.findViewById(SquareImageActivity.IMAGE_ID);
    }

    public void testImageStartsSquare() {

        final View squareImage = getImageViewOnActivity();

        int width = squareImage.getMeasuredWidth();
        int height = squareImage.getMeasuredHeight();

        assertEquals("width and height equal", width, height);

    }


    public void testImageSquareAfterBiggerSize() {

        final View squareImage = getImageViewOnActivity();

        int width = squareImage.getMeasuredWidth();
        int height = squareImage.getMeasuredHeight();

        assertEquals("width and height equal", width, height);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                squareImage.getLayoutParams().width *= 2;
                squareImage.invalidate();
            }
        });

        getInstrumentation().waitForIdleSync();

        width = squareImage.getMeasuredWidth();
        height = squareImage.getMeasuredHeight();

        assertEquals("width and height equal after change", width, height);

    }

    public void testImageSquareAfterSmallerSize() {

        final View squareImage = getImageViewOnActivity();

        int width = squareImage.getMeasuredWidth();
        int height = squareImage.getMeasuredHeight();

        assertEquals("width and height equal", width, height);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                squareImage.getLayoutParams().width /= 2;
                squareImage.invalidate();
            }
        });

        getInstrumentation().waitForIdleSync();

        width = squareImage.getMeasuredWidth();
        height = squareImage.getMeasuredHeight();

        assertEquals("width and height equal after change", width, height);

    }




}
