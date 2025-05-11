package com.sravani.livedata;

public class MutableLiveData<T> extends LiveData<T>{

    public MutableLiveData(T value) {
        super(value);
    }

    /**
     * Creates a MutableLiveData with no value assigned to it.
     */
    public MutableLiveData() {
        super();
    }

    @Override
    public void postValue(T value) {

        super.postValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

}
