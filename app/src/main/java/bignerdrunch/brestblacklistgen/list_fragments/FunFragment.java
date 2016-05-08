package bignerdrunch.brestblacklistgen.list_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;

public class FunFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_fun;

    private RecyclerView rvFun;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        rvFun = (RecyclerView) rootView.findViewById(R.id.rvFun);
        layoutManager = new LinearLayoutManager(getActivity());

        rvFun.setLayoutManager(layoutManager);

        return rootView;
    }
}