package ca.gabrielcastro.library.demo.widget;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

public class TestSquareImage extends ActivityInstrumentationTestCase2<SquareImageActivity> {

    public TestSquareImage() {
        super(SquareImageActivity.class);
    }

    /**
     * Start the testing activity and find the SquareImageView
     * @return the square image view
     */
    private View getImageViewOnActivity() {
        Activity activity = getActivity();
        getInstrumentation().waitForIdleSync();
        return activity.findViewById(SquareImageActivity.IMAGE_ID);
    }

    /**
     * Tests that the image is square when the activity is created
     */
    public void testImageStartsSquare() {

        final View squareImage = getImageViewOnActivity();

        int width = squareImage.getMeasuredWidth();
        int height = squareImage.getMeasuredHeight();

        assertEquals("width and height equal", width, height);

    }


    /**
     * Tests that the image remains square after the width is set 2 times bigger
     */
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

    /**
     * Tests that the image is still square after the width is set to half
     */
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
