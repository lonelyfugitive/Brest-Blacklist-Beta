package bignerdrunch.brestblacklistgen.list_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.adapter.TransportAdapter;
import bignerdrunch.brestblacklistgen.model.ModelCard;

public class TransportFragment extends CrimeFragment {

    private TransportAdapter transportAdapter;

    public TransportFragment(){

    }

    private static final int LAYOUT = R.layout.fragment_transport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvTransport);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        transportAdapter = new TransportAdapter(this);
        recyclerView.setAdapter(transportAdapter);

        return rootView;
    }

    public void addCrime(ModelCard modelCard) {

        int position = -1;

        for (int i = 0; i < transportAdapter.getItemCount(); i++) {

            ModelCard crime = (ModelCard) transportAdapter.getItem(i);
            if (modelCard.getDate() > crime.getDate()) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            transportAdapter.addItem(position, modelCard);
        } else {
            transportAdapter.addItem(modelCard);
        }
    }

    @Override
    public void checkAdapter() {
        if (adapter != null){
            adapter = new TransportAdapter(this);
        }
    }
}