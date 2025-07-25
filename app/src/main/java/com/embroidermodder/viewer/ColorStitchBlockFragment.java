package com.embroidermodder.viewer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.embroidermodder.library.EmbPattern;


public class ColorStitchBlockFragment extends Fragment implements EmbPattern.Listener {
    public static final String TAG = "ColorStitch";
    private ColorStitchAdapter adapter;
    private RecyclerView recyclerColorStitch;

    public ColorStitchBlockFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_stitch_block, container, false);
    }

    public void setPattern(EmbPattern pattern) {
        if (pattern != null) {
            pattern.addListener(this);
            if (adapter == null) {
                adapter = new ColorStitchAdapter();
                adapter.setPattern(pattern);
                recyclerColorStitch.setAdapter(adapter);
            } else {
                adapter.setPattern(pattern);
            }

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerColorStitch = (RecyclerView) view.findViewById(R.id.recyclerColorStitch);
        Context context = view.getContext();
        recyclerColorStitch.setLayoutManager(new LinearLayoutManager(context));
        if (getActivity() instanceof EmbPattern.Provider) {
            setPattern(((EmbPattern.Provider) getActivity()).getPattern());
        }
    }

    @Override
    public void notifyChange(int id) {
        if (id == EmbPattern.NOTIFY_CHANGE) {
            if (getActivity() instanceof EmbPattern.Provider) {
                setPattern(((EmbPattern.Provider) getActivity()).getPattern());
            }
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
