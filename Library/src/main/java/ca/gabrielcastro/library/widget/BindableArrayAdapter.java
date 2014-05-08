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

package ca.gabrielcastro.library.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Makes a extension to {@link android.widget.ArrayAdapter} to use the
 * New/Bind View pattern
 *
 * TODO: create me some unit tests
 *
 * @param <T>
 */
public abstract class BindableArrayAdapter<T> extends ArrayAdapter<T> {

    /**
     * Creates a new BindableArrayAdapter<T>
     *
     * @param context used to for view inflation
     */
    public BindableArrayAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(parent);
        }
        bind(convertView, getItem(position), position);
        return convertView;
    }

    /**
     * Gets called when a new View is required by the adapter
     * This method should only <b>create</b> the view, not bind the data to it
     * @param parent the container for the View
     * @return A new unbound view
     */
    @NonNull
    protected abstract View newView(ViewGroup parent);

    /**
     * Called when an object to be bound to its view
     * @param view the view to bind to
     * @param obj the object to bind
     * @param position the position of the row
     */
    protected abstract void bind(@NonNull View view, T obj, int position);
}
