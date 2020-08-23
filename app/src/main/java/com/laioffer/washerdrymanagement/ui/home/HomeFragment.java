package com.laioffer.washerdrymanagement.ui.home;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import com.laioffer.washerdrymanagement.databinding.FragmentHomeBinding;

import com.laioffer.washerdrymanagement.R;
import com.laioffer.washerdrymanagement.database.DataRepository;
import com.laioffer.washerdrymanagement.database.ViewModelFactory;
import com.laioffer.washerdrymanagement.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private HomeAdapter washerAdapter;
    private FragmentHomeBinding binding;
    public static int state_filter;
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("aaaa", "creating home");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
       }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("aaaa", "created home");
        DataRepository repository = new DataRepository(requireContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        washerAdapter = new HomeAdapter(requireContext(), state_filter);
        binding.washerRecyclerView.setLayoutManager(gridLayoutManager);
        binding.washerRecyclerView.setAdapter(washerAdapter);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(repository))
                .get(HomeViewModel.class);
        viewModel.getWashers()
                .observeForever(
                        response -> {
                            if (response != null && response.size() != 0) {
                                Log.d("aaaa", response.get(0).item_id);
                                washerAdapter.setWashers(response);
                            }
                        }
                );

        SwipeRefreshLayout swipeContainer = binding.swipeContainer;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getWashers()
                        .observe(
                                getViewLifecycleOwner(),
                                response -> {
                                    washerAdapter.setWashers(response);
                                }
                        );
                swipeContainer.setRefreshing(false);
            }
        });

    }
}
