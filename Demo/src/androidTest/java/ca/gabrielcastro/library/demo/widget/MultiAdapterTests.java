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

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.gabrielcastro.library.Helpers;
import ca.gabrielcastro.library.widget.MultiAdapter;

/**
 * Runs tests from {@link ca.gabrielcastro.library.widget.MultiAdapter}
 *
 * TODO: test adapter inside a list view to test view recycle
 */
public class MultiAdapterTests extends AndroidTestCase {

    private static final String lettersHead = "letters";
    private static final String[] letterItems = new String[]{"a", "b", "c", "d", "e"};

    private static final String numbersHead = "numbers";
    private static final String[] numbersItems = new String[]{"1", "2", "3"};

    private static final String symbolsHead = "symbols";
    private static final String[] symbolsItems = new String[]{"!", "@", "#", "$", "%"};

    private static final String[] all;

    static {

        List<String> items = new ArrayList<>();

        items.add(lettersHead);
        items.addAll(Arrays.asList(letterItems));

        items.add(numbersHead);
        items.addAll(Arrays.asList(numbersItems));

        items.add(symbolsHead);
        items.addAll(Arrays.asList(symbolsItems));

        all = items.toArray(new String[items.size()]);
    }

    /**
     * TODO: document this method
     */
    public void testAllContents() throws Exception {
        Helpers.runOnUiAndWait(new Runnable() {
            @Override
            public void run() {

                Context context = getContext();
                final ViewGroup parentView = new LinearLayout(context);
                final MultiAdapter adapter = new MultiAdapter(context, 5);

                adapter.addSection(lettersHead, new ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_list_item_1,
                        letterItems
                ));
                adapter.addSection(numbersHead, new ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_list_item_1,
                        numbersItems
                ));
                adapter.addSection(symbolsHead, new ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_list_item_1,
                        symbolsItems
                ));

                for (int i = 0; i < all.length; ++i) {
                    View itemView = adapter.getView(i, null, parentView);
                    assertNotNull(itemView);

                    TextView textView = (TextView) itemView.findViewById(android.R.id.text1);
                    assertNotNull(textView);

                    CharSequence cs = textView.getText();
                    assertNotNull(cs);
                    String text = cs.toString();
                    assertNotNull(text);

                    assertEquals("list item{" + i + "} expected", all[i], text);

                }

            }
        });
    }


}
