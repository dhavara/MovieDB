package com.example.moviedb.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.adapter.UpComingAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.UpComing;
import com.example.moviedb.viewmodel.MovieViewModel;

public class UpComingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpComingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rv_upcoming;
    private MovieViewModel view_model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

        rv_upcoming = view.findViewById(R.id.rv_upcoming_fragment);
        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getUpComing();
        view_model.getResultUpComing().observe(getActivity(), showUpComing);

        return view;
    }

    private Observer<UpComing> showUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            rv_upcoming.setLayoutManager(new LinearLayoutManager(getActivity()));
            UpComingAdapter adapter = new UpComingAdapter(getActivity());
            adapter.setListUpComing(upComing.getResults());
            rv_upcoming.setAdapter(adapter);

            ItemClickSupport.addTo(rv_upcoming).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+upComing.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate((R.id.action_upComingFragment_to_movieDetailsFragment), (bundle));

                }
            });
        }
    };
}