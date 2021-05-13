package ua.kpi.comsys.iv8230;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static ua.kpi.comsys.iv8230.MainActivity.GALLERY_REQUEST;

public class ImagesFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }
}