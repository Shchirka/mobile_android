package ua.kpi.comsys.iv8230;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ImagesFragment extends Fragment {
    public static final int GALLERY_REQUEST = 1;
    public ImageView image;
    public int image_index = 0;
    public int index = 0;
    int c = 0;
    int r = 0;
    public int presses;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayout gridLayout = getView().findViewById(R.id.images_layout);

        presses = 0;
        ImageButton button = getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent;
                image = new ImageView(getView().getContext());
                switch (presses){
                    case 0:
                        for (int i = 0; i < 10; i++){
                            CardView cv = new CardView(getView().getContext());
                            cv.setId(index);
                            GridLayout.LayoutParams parameters = new GridLayout.LayoutParams();
                            parameters.width = 0;
                            if(((c == 1)&&(r%4 == 0))||((c == 0)&&(r%4 == 2))){
                                parameters.rowSpec = GridLayout.spec(r, 2, 2f);
                                parameters.columnSpec = GridLayout.spec(c, 2, 2f);
                                parameters.height = 500;
                            }
                            else{
                                parameters.rowSpec = GridLayout.spec(r, 1f);
                                parameters.columnSpec = GridLayout.spec(c, 1f);
                                parameters.height = 250;
                            }
                            gridLayout.addView(cv, parameters);
                            index++;
                            switch (i){
                                case 0:
                                case 6:
                                case 8:
                                    c++;
                                    break;
                                case 1:
                                case 5:
                                    c += 2;
                                    break;
                                case 2:
                                case 4:
                                case 9:
                                    c = 0;
                                    r++;
                                    break;
                                case 3:
                                    c += 3;
                                    break;
                                case 7:
                                    c--;
                                    r++;
                                    break;
                            }
                        }
                        CardView cardView1 = getView().findViewById(image_index);
                        photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                        cardView1.addView(image);
                        presses += 1;
                        image_index++;
                        break;
                    case 6:
                    case 8:
                    case 1:
                    case 5:
                    case 2:
                    case 4:
                    case 3:
                    case 7:
                        cardView1 = getView().findViewById(image_index);
                        photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                        cardView1.addView(image);
                        presses += 1;
                        image_index++;
                        break;
                    case 9:
                        cardView1 = getView().findViewById(image_index);
                        photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                        cardView1.addView(image);
                        presses = 0;
                        image_index++;
                        break;
                }
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

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.setImageBitmap(bitmap);
                    image.setBackgroundColor(Color.GRAY);
                }
        }
    }
}