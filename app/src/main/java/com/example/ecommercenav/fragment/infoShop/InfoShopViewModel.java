package com.example.ecommercenav.fragment.infoShop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfoShopViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InfoShopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is infoShop fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}