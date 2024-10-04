package com.example.netnews.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.netnews.R;
import com.example.netnews.adapter.NewsAdapter;
import com.example.netnews.authentication.AuthViewModel;
import com.example.netnews.databinding.FragmentCategoryBinding;
import com.example.netnews.model.Data;
import com.example.netnews.viewmodel.NewsViewModel;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private NewsViewModel viewModel;
    private ArrayList<Data> dataArrayList;
    private NewsAdapter adapter;
    private RecyclerView newsRV;

    public String topic = "";


    public CategoryFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category,
                container,
                false
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this)
                .get(NewsViewModel.class);

        dataArrayList = new ArrayList<>();

        getLatestNews();
    }

    private void getLatestNews() {
        viewModel.getSectionNews(topic).observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                dataArrayList = (ArrayList<Data>) data;
                displayLatestNews();
            }
        });

    }

    private void displayLatestNews() {
        newsRV = binding.newsRecyclerView;
        newsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), dataArrayList, viewModel,getViewLifecycleOwner());
        newsRV.setAdapter(adapter);
    }
}