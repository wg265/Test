package com.laioffer.washerdrymanagement.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laioffer.washerdrymanagement.database.DataRepository;
import com.laioffer.washerdrymanagement.database.Item;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private DataRepository repository;
    public HomeViewModel(DataRepository repository) {
        this.repository = repository;
    }
    public LiveData<List<Item>> getWashers() {
        return repository.getWashers();
    }

}
