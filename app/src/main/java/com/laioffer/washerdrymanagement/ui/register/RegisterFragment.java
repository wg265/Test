package com.laioffer.washerdrymanagement.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laioffer.washerdrymanagement.R;
import com.laioffer.washerdrymanagement.base.BaseFragment;
import com.laioffer.washerdrymanagement.base.BaseRepository;
import com.laioffer.washerdrymanagement.base.BaseViewModel;
import com.laioffer.washerdrymanagement.databinding.FragmentRegisterBinding;
import com.laioffer.washerdrymanagement.remote.RemoteResponseListener;
import com.laioffer.washerdrymanagement.remote.response.RemoteResponse;
import com.laioffer.washerdrymanagement.remote.response.UserInfo;
import com.laioffer.washerdrymanagement.ui.home.HomeFragment;
import com.laioffer.washerdrymanagement.ui.NavigationManager;
import com.laioffer.washerdrymanagement.ui.login.LoginFragment;
import com.laioffer.washerdrymanagement.ui.login.LoginRepository;
import com.laioffer.washerdrymanagement.ui.login.LoginViewModel;
import com.laioffer.washerdrymanagement.util.Utils;

public class RegisterFragment extends BaseFragment<RegisterViewModel, RegisterRepository> {

    private FragmentRegisterBinding binding;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.btnRegister.setOnClickListener( v -> {
            viewModel.register(new RegisterEvent(binding.etUserId.getText().toString(),
                    binding.etPassword.getText().toString(),
                    binding.etFirstName.getText().toString(),
                    binding.etLastName.getText().toString(),
                    binding.etPhoneNumber.getText().toString()));
        });
        viewModel.getErrMsgMutableLiveData().observe(getViewLifecycleOwner(), errMsg -> {
            Utils.constructToast(getContext(), errMsg).show();
        });
        viewModel.getRemoteResponseLiveData().observe(getViewLifecycleOwner(), it -> {
            if (it == null) {
                Utils.constructToast(getContext(), "Error! empty response body!").show();
            } else {
                Utils.constructToast(getContext(), it.status).show();
                // do we need to redirect to the userInfo fragment?
            }
        });
    }

    @Override
    protected RegisterViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), getFactory()).get(RegisterViewModel.class);
    }

    @Override
    protected ViewModelProvider.Factory getFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RegisterViewModel(getRepository());
            }
        };
    }

    @Override
    protected RegisterRepository getRepository() {
        return new RegisterRepository();
    }

}
