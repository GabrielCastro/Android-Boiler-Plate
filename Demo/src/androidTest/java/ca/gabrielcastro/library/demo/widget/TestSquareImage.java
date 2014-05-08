/*
 * Copyright 2013 - 2014 Gabriel Castro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
