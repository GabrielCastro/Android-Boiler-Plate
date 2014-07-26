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

package ca.gabrielcastro.library.ui;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * A TextWatcher that limits the max number in a watchable text
 */
public class MaxValueFormatter implements TextWatcher {

    private final int mMax;

    /**
     * Creates a new MaxValueFormatter that limits the text to a max value
     * @param maxValue the max value
     * @throws java.lang.IllegalArgumentException if {@code maxValue <= 0}
     */
    public MaxValueFormatter(int maxValue) {
        if (maxValue <= 0) {
            throw new IllegalArgumentException("max Value must be <= 0 current = " + maxValue);
        }
        this.mMax = maxValue;
    }

    /**
     * Not Used
     * {@inheritDoc}
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * Not Used
     * {@inheritDoc}
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * Dose all the hard work.
     * should not be called directly
     * {@inheritDoc}
     * @throws java.lang.NullPointerException if {@code editable == null}
     */
    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            return;
        }
        int value = Integer.parseInt(editable.toString());
        if (value <= mMax) {
            return;
        }
        while (value > mMax) {
            int lastChar = editable.length() - 1;
            editable.delete(lastChar, lastChar + 1);

            value = editable.length() > 0 ? Integer.parseInt(editable.toString()) : 0;
        }
    }
}
