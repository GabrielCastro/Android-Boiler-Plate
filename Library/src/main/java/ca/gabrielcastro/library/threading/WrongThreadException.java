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

package ca.gabrielcastro.library.threading;

import android.os.Looper;

public class WrongThreadException extends RuntimeException {

    public WrongThreadException() {
        super("method called from wrong thread");
    }

    public WrongThreadException(String message) {
        super(message);
    }

    public static void ensureMain() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new WrongThreadException("method must be called from the main thread, called form \""
                    + Thread.currentThread().getName() + '"');
        }
    }

    public static void ensureNotMain() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new WrongThreadException("method cannot be called from main thread");
        }
    }
}