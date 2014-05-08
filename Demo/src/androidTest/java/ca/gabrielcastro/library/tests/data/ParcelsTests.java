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

package ca.gabrielcastro.library.tests.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.test.AndroidTestCase;

import ca.gabrielcastro.library.data.Parcels;

/**
 * Tests {@link ca.gabrielcastro.library.data.Parcels} utility class
 *
 * TODO: more mixed tests
 */
public class ParcelsTests extends AndroidTestCase {


    /**
     * Tests that null values are written and read properly
     */
    public void testAllNulls() {

        Parcel outParcel = Parcel.obtain();

        Parcels.writeBool(outParcel, false);
        Parcels.writeNullable(outParcel, null, 0);
        Parcels.writeNullableString(outParcel, null);

        byte[] marshalled = outParcel.marshall();
        outParcel.recycle();

        Parcel inParcel = Parcel.obtain();
        inParcel.unmarshall(marshalled, 0, marshalled.length);

        boolean shouldBeFalse = Parcels.readBool(inParcel);
        Parcelable shouldBeNullObj = Parcels.readNullable(inParcel, Bundle.class);
        String shouldBeNullStr = Parcels.readNullableString(inParcel);

        assertFalse(shouldBeFalse);
        assertNull(shouldBeNullObj);
        assertNull(shouldBeNullStr);

        inParcel.recycle();

    }

}
