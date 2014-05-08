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

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ca.gabrielcastro.library.demo.app.BaseActivity;
import ca.gabrielcastro.library.widget.SquareImageView;

/**
 * An activity used to test SquareImageView
 */
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
