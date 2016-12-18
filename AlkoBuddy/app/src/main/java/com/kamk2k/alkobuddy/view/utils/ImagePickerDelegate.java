package com.kamk2k.alkobuddy.view.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.kamk2k.alkobuddy.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import javax.inject.Inject;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kksiazek on 26.09.16.
 */

public class ImagePickerDelegate {
    private static final String TAG = "ImagePickerDelegate";
    public static final int COMPRESS_QUALITY = 75;
    public static final Bitmap.CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    public static final String IMAGE_CACHE_DIRECTORY_PATH = "DrinkImages";
    public static final String IMAGE_CACHE_FILE_NAME_TEMPLATE = "drink_image_%d.jpeg";

    private OnCompleteListener onCompleteListener;

    public interface OnCompleteListener {
        void onImageReturned(Uri imageUri);
        void onError();
    }

    @Inject
    public ImagePickerDelegate() {
    }

    public ImagePickerDelegate addOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
        return this;
    }

    public void startImagePicker(Fragment fragment) {
        EasyImage.openChooserWithGallery(fragment, fragment.getContext().getString(R.string.image_picker_title), 1);
    }

    public void handleActivityResult(Activity activity, Fragment fragment, int requestCode, int resultCode, Intent data, int drinkId) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handlingcontext.getFilesDir().get
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                launchUCrop(fragment, imageFile, drinkId);
            }
        });
        if (isUCropRequestResult(requestCode, resultCode)) {
            handleUCropRequestResult(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            // TODO: 22.09.16 error handling
            final Throwable cropError = UCrop.getError(data);
            Log.e(TAG, "UCrop error = " + cropError);
        }
    }

    private void handleUCropRequestResult(Intent data) {
        final Uri resultUri = UCrop.getOutput(data);
        if(onCompleteListener != null) {
            onCompleteListener.onImageReturned(resultUri);
        }
    }

    private boolean isUCropRequestResult(int requestCode, int resultCode) {
        return resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP;
    }

    private void launchUCrop(Fragment fragment, File imageFile, int drinkId) {
        Context context = fragment.getContext();

        File drinkImageFile = getFileForDrink(context, drinkId);
        if(drinkImageFile.exists())
            drinkImageFile.delete();

        UCrop.Options options = new UCrop.Options();
        Bitmap.CompressFormat compressFormat = COMPRESS_FORMAT;
        int compressQuality = COMPRESS_QUALITY;
        options.setCompressionFormat(compressFormat);
        options.setCompressionQuality(compressQuality);
        options.setToolbarColor(ContextCompat.getColor(context, R.color.primary));
        options.setStatusBarColor(ContextCompat.getColor(context, R.color.primary));

        UCrop.of(Uri.fromFile(imageFile), Uri.fromFile(drinkImageFile))
                .withAspectRatio(4, 4)
                .withMaxResultSize(600, 600)
                .withOptions(options)
                .start(context, fragment);
    }

    private File getFileForDrink(Context context, int drinkId) {
        File imageCacheDir = new File(context.getFilesDir(), IMAGE_CACHE_DIRECTORY_PATH);
        if (!imageCacheDir.exists())
            imageCacheDir.mkdirs();
        return new File(imageCacheDir, String.format(IMAGE_CACHE_FILE_NAME_TEMPLATE, drinkId));
    }
}
