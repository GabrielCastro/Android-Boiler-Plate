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

package ca.gabrielcastro.library;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

public class Helpers {


    private static Handler uiThread = new Handler(Looper.getMainLooper());


    /**
     * We need this because @UiThreadTest doesn't work here from some reason
     *
     * <br/>
     * <b>WARNING:</b> dead lock if called from the UI thread, Don't use this outside tests
     *
     * @param r what to run
     */
    public static void runOnUiAndWait(final Runnable r) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                r.run();
                latch.countDown();
            }
        });
        latch.await();
    }

}