package com.laioffer.washerdrymanagement.ui.reservation;

import android.os.Bundle;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.laioffer.washerdrymanagement.R;
import com.laioffer.washerdrymanagement.database.DataRepository;
import com.laioffer.washerdrymanagement.database.ViewModelFactory;
import com.laioffer.washerdrymanagement.databinding.FragmentReservationBinding;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {
    private ReservationViewModel viewModel;
    private FragmentReservationBinding binding;
    private ReservationAdapter reservationAdapter;
    public static ReservationFragment newInstance() {
        Bundle args = new Bundle();
        ReservationFragment fragment = new ReservationFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReservationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reservationAdapter.timer.cancel();
        reservationAdapter.timer = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataRepository repository = new DataRepository(getContext());
        reservationAdapter = new ReservationAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        binding.reservationRecyclerView.setLayoutManager(gridLayoutManager);
        binding.reservationRecyclerView.setAdapter(reservationAdapter);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(repository)).get(ReservationViewModel.class);
        viewModel.getReservations().observe(
                getViewLifecycleOwner(),
                response -> {
                    reservationAdapter.setReservation(response);
                }
        );
        SwipeRefreshLayout swipeContainer = binding.reservationContainer;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getReservations().observe(
                        getViewLifecycleOwner(),
                        response -> {
                            reservationAdapter.setReservation(response);
                        }
                );
                swipeContainer.setRefreshing(false);
            }
        });
        binding.finishReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ReservationAdapter.selected != null) {
                    swipeContainer.setRefreshing(true);
                    for (Integer index : ReservationAdapter.selected) {
                        repository.deleteReservaiton(ReservationAdapter.reservations.get(index).item_id);
                    }
                    viewModel.getReservations().observe(
                            getViewLifecycleOwner(),
                            response -> {
                                reservationAdapter.setReservation(response);
                            }
                    );
                    swipeContainer.setRefreshing(false);
                }
            }
        });

    }
}
