package com.sravani.livedata;


import static androidx.lifecycle.Lifecycle.State.DESTROYED;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;


import java.util.HashMap;
import java.util.Map;

public abstract class LiveData<T> {

    private volatile Object mData;

    static final Object NOT_SET = new Object();
    Handler handler = new Handler(Looper.myLooper());
    private final Map<LifecycleOwner, Observer<T>> observers = new HashMap<>();
    public LiveData(T value) {
        mData = value;
    }

    /**
     * Creates a LiveData with no value assigned to it.
     */
    public LiveData() {
        mData = NOT_SET;

    }

    @Nullable
    public T getValue() {
        return (T) mData;
    }

   protected void setValue(T value){
       this.mData = value;
       Log.v("livedata", "value = "+value);
       Log.v("livedata", "size = "+observers.size());
       // Notify all active observers
       for (Map.Entry<LifecycleOwner, Observer<T>> entry : observers.entrySet()) {
           LifecycleOwner owner = entry.getKey();
           Observer<T> observer = entry.getValue();

           // Only notify observers whose lifecycle is active
           if (owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
               observer.onChanged(value);
           }
       }
   }

   protected void postValue(T value){
       handler.post(new Runnable() {
           @Override
           public void run() {
              setValue(value);
           }
       });
   }
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        Log.v("livedata", "observer = "+observer);
        observers.put(owner, observer);
        // Listen for lifecycle events
        owner.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                // If lifecycle goes to destroyed, remove the observer
                if (event == Lifecycle.Event.ON_DESTROY) {
                    observers.remove(owner);
                }

                // If the lifecycle is active, notify observer
                if (event == Lifecycle.Event.ON_START || event == Lifecycle.Event.ON_RESUME) {
                    if (mData != null) {
                        observer.onChanged((T)mData);
                    }
                }
            }
        });
    }

}