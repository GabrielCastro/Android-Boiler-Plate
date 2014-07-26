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
import android.text.SpannableStringBuilder;

import junit.framework.TestCase;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown;


public class MaxValueFormatterTests extends TestCase {

    public void testMaxAllowedValueZero() {
        try {
            new MaxValueFormatter(0);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .hasNoCause()
                    .hasMessageStartingWith("max Value must be <= 0 current =");
        }
    }

    public void testMaxAllowedLessThanZero() {
        try {
            new MaxValueFormatter(-2);
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .hasNoCause()
                    .hasMessageStartingWith("max Value must be <= 0 current =");
        }
    }

    public void testSingleDigit() {
        MaxValueFormatter fmt = new MaxValueFormatter(8);

        Editable text = new SpannableStringBuilder("8");

        fmt.afterTextChanged(text);

        assertThat(text.toString())
                .isEqualTo("8");

        text = new SpannableStringBuilder("56");

        fmt.afterTextChanged(text);

        assertThat(text.toString())
                .isEqualTo("5");

        text = new SpannableStringBuilder("9");

        fmt.afterTextChanged(text);

        assertThat(text.toString())
                .isEmpty();

    }

    public void testDoubleDigit() {
        MaxValueFormatter fmt = new MaxValueFormatter(24);

        Editable text = new SpannableStringBuilder("24");

        fmt.afterTextChanged(text);
        assertThat(text.toString()).isEqualTo("24");

        text = new SpannableStringBuilder("30");
        fmt.afterTextChanged(text);
        assertThat(text.toString()).isEqualTo("3");

        text = new SpannableStringBuilder("26");
        fmt.afterTextChanged(text);
        assertThat(text.toString()).isEqualTo("2");

    }

    public void testEmpty() {
        MaxValueFormatter fmt = new MaxValueFormatter(57);
        Editable text = new SpannableStringBuilder("");
        fmt.afterTextChanged(text);
        assertThat(text.toString()).isEmpty();
    }

    public void testNull() {
        MaxValueFormatter fmt = new MaxValueFormatter(88);
        try {
            fmt.afterTextChanged(null);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            assertThat(e)
                    .hasNoCause();
        }
    }

}
