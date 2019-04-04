package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.rusa_app.R;
import com.ats.rusa_app.adapter.RvGalleryAdapter;
import com.ats.rusa_app.interfaces.PhotosInterface;

import static com.ats.rusa_app.fragment.GalleryDisplayFragment.staticPhotosList;

public class PhotosFragment extends Fragment implements PhotosInterface {

    private RecyclerView rvPhotos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        rvPhotos = view.findViewById(R.id.rvPhotos);

        RvGalleryAdapter adapter = new RvGalleryAdapter(staticPhotosList, getContext());
        rvPhotos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvPhotos.setItemAnimator(new DefaultItemAnimator());
        rvPhotos.setAdapter(adapter);

        return view;
    }


    @Override
    public void fragmentBecameVisible() {
        RvGalleryAdapter adapter = new RvGalleryAdapter(staticPhotosList, getContext());
        rvPhotos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvPhotos.setItemAnimator(new DefaultItemAnimator());
        rvPhotos.setAdapter(adapter);

    }
}
