package ca.gabrielcastro.library.threading;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

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
        mHandler.post(command);
    }

}