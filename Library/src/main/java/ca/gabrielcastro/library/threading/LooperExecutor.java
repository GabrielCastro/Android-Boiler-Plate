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

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * An Implementation of an {@link java.util.concurrent.Executor} that will run on a
 * {@link android.os.Looper} by creating a Handler on it.
 *
 * Useful for many Futures based APIs for example Guavas Futures
 *
 * @see java.util.concurrent.Executor
 * @see android.os.Looper
 */
public class LooperExecutor implements Executor {

    private final Handler mHandler;

    /**
     * Creates an Executor defaulting to the UI thread
     */
    public LooperExecutor() {
        this(Looper.getMainLooper());
    }

    /**
     * Creates an Executor on the given Looper
     * @param looper The Looper to run on
     * @see android.os.Looper
     */
    public LooperExecutor(@NonNull Looper looper) {
        mHandler = new Handler(looper);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        if (!mHandler.post(command)) {
            throw new RejectedExecutionException();
        }
    }

}