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
import android.database.DataSetObserver;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ca.gabrielcastro.library.threading.WrongThreadException;

/**
 * An adapter used to make sections with headers
 * <p/>
 * <br/>
 * TODO: maybe make a matching sticky headers list view
 */
public class MultiAdapter extends BaseAdapter {


    private ArrayAdapter<CharSequence> mHeaderAdapter;
    private ArrayList<BaseAdapter> mSectionAdapters;
    private final int mMaxCount;
    private int mSize;


    /**
     * Creates a MultiAdapter with a default header adapter of
     * {@code ArrayAdapter<>(context, android.R.layout.simple_list_item_1)}
     *
     * @param context     used for layout inflation
     * @param maxSections the expected number of sections, if there is an attempt to
     *                    add more than {@code maxSections} sections an {@link TooManySectionsException}
     *                    will be thrown
     */
    public MultiAdapter(Context context, int maxSections) {
        mHeaderAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        mSectionAdapters = new ArrayList<>(maxSections);
        mMaxCount = maxSections;
        mSize = 0;
    }

    /**
     * Creates a MultiAdapter that uses the given adapter for headers
     *
     * @param maxSections   the expected number of sections, if there is an attempt to
     *                      add more than {@code maxSections} sections an {@link TooManySectionsException}
     *                      will be thrown
     * @param headerAdapter the adapter to use for headers the contents of which will be modified
     *                      by the MultiAdapter and should not be modified else where or there
     *                      will be unexpected results
     */
    public MultiAdapter(int maxSections, ArrayAdapter<CharSequence> headerAdapter) {
        mHeaderAdapter = headerAdapter;
        mSectionAdapters = new ArrayList<>(maxSections);
        mMaxCount = maxSections;
        mSize = 0;
    }

    /**
     * Add a section to the Adapter
     * <br/>
     * Must be called on the main thread
     *
     * @param title          for the header
     * @param sectionAdapter for the section
     * @throws java.lang.IllegalStateException                                       if called off the main thread
     * @throws ca.gabrielcastro.library.widget.MultiAdapter.TooManySectionsException if there are already the max number of sections set
     */
    public void addSection(CharSequence title, BaseAdapter sectionAdapter) {
        WrongThreadException.ensureMain();
        if (mSectionAdapters.size() == mMaxCount) {
            throw new TooManySectionsException(mMaxCount);
        }
        mHeaderAdapter.add(title);
        mSectionAdapters.add(sectionAdapter);
        sectionAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                MultiAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                MultiAdapter.this.notifyDataSetInvalidated();
            }
        });

        notifyDataSetChanged();
    }

    /**
     * Should <b>not</b> be called directly.
     * Instead call {@link super#notifyDataSetChanged} on the appropriate section adapter
     * because this will not be called through the sections
     */
    @Override
    public void notifyDataSetChanged() {
        int size = 0;
        for (BaseAdapter adapter : mSectionAdapters) {
            // header
            ++size;
            size += adapter.getCount();
        }
        this.mSize = size;
        super.notifyDataSetChanged();
    }

    /**
     * Should <b>not</b> be called directly.
     * Instead call {@link super#notifyDataSetInvalidated} on the appropriate section adapter
     * because this will not be called through the sections
     */
    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public Object getItem(int position) {
        Pair<? extends BaseAdapter, Integer> adapterAndAdapterPos = getAdapterAndAdapterPos(position);
        return adapterAndAdapterPos.first.getItem(adapterAndAdapterPos.second);
    }

    @Override
    public long getItemId(int position) {
        Pair<? extends BaseAdapter, Integer> adapterAndAdapterPos = getAdapterAndAdapterPos(position);
        return adapterAndAdapterPos.first.getItemId(adapterAndAdapterPos.second);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<? extends BaseAdapter, Integer> adapterAndAdapterPos = getAdapterAndAdapterPos(position);
        return adapterAndAdapterPos.first.getView(adapterAndAdapterPos.second, convertView, parent);
    }

    /**
     * @return true if all sections return true for this method
     */
    @Override
    public boolean hasStableIds() {
        WrongThreadException.ensureMain();
        boolean stableIds = mHeaderAdapter.hasStableIds();
        for (BaseAdapter adapter : mSectionAdapters) {
            stableIds &= adapter.hasStableIds();
        }
        return stableIds;
    }

    @Override
    public int getItemViewType(int position) {
        Pair<? extends BaseAdapter, Integer> adapterAndAdapterPos = getAdapterAndAdapterPos(position);
        if (adapterAndAdapterPos.first == mHeaderAdapter) {
            return 0;
        }
        return this.mSectionAdapters.indexOf(adapterAndAdapterPos.first) + 1;
    }

    @Override
    public int getViewTypeCount() {
        return mMaxCount + 1;
    }

    /**
     * Gets the adapter and the position inside that adapter for an absolute position
     *
     * @param position the absolute position in the MultiAdapter
     * @return Pair&lt;BaseAdapter, Integer&gt; The section adapter and position in that adapter
     */
    private Pair<? extends BaseAdapter, Integer> getAdapterAndAdapterPos(int position) {
        if (position < 0 || position > mSize) {
            throw new IllegalArgumentException("position " + position + " is  < 0 or > mSize");
        }

        int adapterNum = 0;
        int adapterPos = position - 1;
        do {
            if (adapterPos == -1) {
                return new Pair<>(mHeaderAdapter, adapterNum);
            }
            BaseAdapter adapter = mSectionAdapters.get(adapterNum);
            if (adapterPos < adapter.getCount()) {
                return new Pair<>(adapter, adapterPos);
            }
            adapterPos--;
            adapterPos -= adapter.getCount();
            adapterNum++;
        } while (true);
    }

    /**
     * Thrown by MultiAdapter if too many sections are added
     */
    public static class TooManySectionsException extends IllegalStateException {

        private TooManySectionsException(int maxSections) {
            super("attempting to add a section after the limit of " + maxSections + " was reached");
        }

    }
}
