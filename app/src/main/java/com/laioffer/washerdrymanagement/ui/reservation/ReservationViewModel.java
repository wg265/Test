package com.laioffer.washerdrymanagement.ui.reservation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.laioffer.washerdrymanagement.database.DataRepository;
import com.laioffer.washerdrymanagement.database.Reservation;
import com.laioffer.washerdrymanagement.util.Config;

import java.util.ArrayList;
import java.util.List;

public class ReservationViewModel extends ViewModel {
    private DataRepository repository;
    public ReservationViewModel(DataRepository repository) {
        this.repository = repository;
    }
    public LiveData<List<Reservation>> getReservations (){
        return repository.getReservations();
    }
}
