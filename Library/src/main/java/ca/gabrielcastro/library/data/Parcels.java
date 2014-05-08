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

package ca.gabrielcastro.library.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A class for help working with parcels
 */
public class Parcels {

    private Parcels() {
        // NO Objects only static functions
    }

    /**
     * Write a parcelable object that may be null
     *
     * @param out   destination parcel
     * @param obj   object to write
     * @param flags flags for writing the object
     */
    public static void writeNullable(@NonNull Parcel out, @Nullable Parcelable obj, int flags) {
        if (obj == null) {
            out.writeByte((byte) 0);
        } else {
            out.writeByte((byte) 1);
            out.writeParcelable(obj, flags);
        }
    }

    /**
     * Read an object written with {@link #writeNullable(android.os.Parcel, android.os.Parcelable, int)}
     *
     * @param in    The source parcel
     * @param klass The class of the object to read
     * @param <T>   A Parcelable class
     * @return The stored object which may be null
     */
    @Nullable
    public static <T extends Parcelable> T readNullable(@NonNull Parcel in, @NonNull Class<T> klass) {
        byte b = in.readByte();
        if (b == 0) {
            return null;
        }
        return in.readParcelable(klass.getClassLoader());
    }


    /**
     * Write a string that may be null to a parcel
     *
     * @param out Destination Parcel
     * @param str Nullable string to write
     */
    public static void writeNullableString(@NonNull Parcel out, @Nullable String str) {
        if (str == null) {
            out.writeByte((byte) 0);
        } else {
            out.writeByte((byte) 1);
            out.writeString(str);
        }
    }

    /**
     * Read a String written with {@link #writeNullableString(android.os.Parcel, String)}
     *
     * @param in source Parcel
     * @return A Nullable String stored in the parcel
     */
    @Nullable
    public static String readNullableString(@NonNull Parcel in) {
        byte isWritten = in.readByte();
        String str = null;
        if (isWritten != 0) {
            str = in.readString();
        }
        return str;
    }


    /**
     * Write a boolean value to a parcel
     *
     * <br/>
     * <b>NOTE:</b> this is easy to use, but writing all booleans at once in an array is more efficient
     *
     * @param out  destination parcel
     * @param flag boolean to write
     */
    public static void writeBool(@NonNull Parcel out, boolean flag) {
        out.writeByte(flag ? (byte) 1 : (byte) 0);
    }

    /**
     * Read a boolean written with {@link #writeBool(android.os.Parcel, boolean)]}
     *
     * @param in source Parcel
     * @return boolean from parcel
     */
    public static boolean readBool(@NonNull Parcel in) {
        byte flag = in.readByte();
        return flag != 0;
    }

}
