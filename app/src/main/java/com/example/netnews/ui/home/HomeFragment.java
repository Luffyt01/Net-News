package com.example.netnews.ui.home;

import static org.imaginativeworld.whynotimagecarousel.utils.Utils.setImage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.netnews.R;
import com.example.netnews.adapter.NewsAdapter;
import com.example.netnews.adapter.TabAdapter;
import com.example.netnews.databinding.FragmentHomeBinding;
import com.example.netnews.databinding.FullImageItemBinding;
import com.example.netnews.model.Data;
import com.example.netnews.ui.categories.AllFragment;
import com.example.netnews.ui.categories.BusinessFragment;
import com.example.netnews.ui.categories.EntertainFragment;
import com.example.netnews.ui.categories.HealthFragment;
import com.example.netnews.ui.categories.ScienceFragment;
import com.example.netnews.ui.categories.SportsFragment;
import com.example.netnews.ui.categories.TechFragment;
import com.example.netnews.ui.categories.WorldFragment;
import com.example.netnews.ui.webview.NewsDetailsActivity;
import com.example.netnews.viewmodel.NewsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.checkerframework.checker.units.qual.A;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NewsViewModel viewModel;

    private ImageCarousel carousel;
    private List<CarouselItem> list;

    private ViewPager2 viewPager2;
    private TabLayout categoryTabs;
    private TabAdapter adapter;
    private ArrayList<Data> dataList;


    public HomeFragment() {
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
                LayoutInflater.from(getContext()),
                R.layout.fragment_home,
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

        carousel = binding.carousel;
        carousel.setShowCaption(false);
        carousel.registerLifecycle(getLifecycle());
        list = new ArrayList<>();

        dataList = new ArrayList<>();
        getLatestNews();



    }

    private void getLatestNews() {
        viewModel.getLatestNews().observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                dataList = (ArrayList<Data>) data;
                for(Data data1: data){
                    list.add(new CarouselItem(data1.getPhotoUrl(),data1.getTitle()));
                }
                carousel.setData(list);
                displayLatestNews();
            }
        });
    }

    private void displayLatestNews() {
        carousel.setCarouselListener(new CarouselListener() {
            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return FullImageItemBinding.inflate(layoutInflater,viewGroup,false);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
                FullImageItemBinding currentBinding = (FullImageItemBinding) viewBinding;
                currentBinding.newsTitle.setText(carouselItem.getCaption());
                Data data = dataList.get(i);
                Glide.with(currentBinding.sourceIcon.getContext()).load(data.getSourceFaviconUrl()).into(currentBinding.sourceIcon);
                currentBinding.timeDifference.setText(data.getPublishedDatetimeUtc());
                setImage(currentBinding.fullNewsPhoto,carouselItem);

            }

            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                Data data = dataList.get(i);
                String link = data.getLink();
                Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                intent.putExtra("link",link);
            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {

            }
        });





        viewPager2 = binding.viewPager2;
        categoryTabs = binding.categoryTab;

        binding.progressBar.setVisibility(View.GONE);
        binding.homeLayout.setVisibility(View.VISIBLE);

        viewPager2.setUserInputEnabled(true);

        adapter = new TabAdapter(getActivity().getSupportFragmentManager(),
                getLifecycle());

        adapter.addFragment(new AllFragment());
//        adapter.addFragment(new WorldFragment());
//        adapter.addFragment(new BusinessFragment());
//        adapter.addFragment(new TechFragment());
//        adapter.addFragment(new EntertainFragment());
//        adapter.addFragment(new SportsFragment());
//        adapter.addFragment(new ScienceFragment());
//        adapter.addFragment(new HealthFragment());

        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        binding.topHeadLayout.setVisibility(View.VISIBLE);

        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(
                categoryTabs,
                viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                        switch (i){
                            case 0:
                                tab.setText("All");
                                break;
//                            case 1:
//                                tab.setText("World");
//                                break;
//                            case 2:
//                                tab.setText("Business");
//                                break;
//                            case 3:
//                                tab.setText("Technology");
//                                break;
//                            case 4:
//                                tab.setText("Entertainment");
//                                break;
//                            case 5:
//                                tab.setText("Sports");
//                                break;
//                            case 6:
//                                tab.setText("Science");
//                                break;
//                            case 7:
//                                tab.setText("Health");
//                                break;
                        }
                    }
                }
        ).attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position>0){
                    binding.topHeadLayout.setVisibility(View.GONE);
                }else{
                    binding.topHeadLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}