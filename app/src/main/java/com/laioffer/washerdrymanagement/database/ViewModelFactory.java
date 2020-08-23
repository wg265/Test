package com.laioffer.washerdrymanagement.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.laioffer.washerdrymanagement.ui.home.HomeViewModel;
import com.laioffer.washerdrymanagement.ui.reservation.ReservationViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final DataRepository repository;
    public ViewModelFactory(DataRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        }
        else if (modelClass.isAssignableFrom(ReservationViewModel.class)) {
            return (T) new ReservationViewModel(repository);
        }
        else {
            throw new IllegalStateException("Unknown ViewModel");
        }
    }
}
