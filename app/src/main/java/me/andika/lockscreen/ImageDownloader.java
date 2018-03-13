package me.andika.lockscreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sergeyteperchuk on 3/13/18.
 */

public class ImageDownloader extends AsyncTask<URL,Integer,List<Bitmap>> {

    private AsyncTask mMyTask;
    private Context context;
    private ProgressDialog mProgressDialog;

    ImageDownloader(Context ctx, ProgressDialog dialog){
        context = ctx;
        mProgressDialog = dialog;
    }

    // Before the tasks execution
    protected void onPreExecute(){
        // Display the progress dialog on async task start

        // Initialize the progress dialog
        mProgressDialog.setIndeterminate(false);
        // Progress dialog horizontal style
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Progress dialog title
        mProgressDialog.setTitle("Скачиваем базу вопросов");
        // Progress dialog message
        mProgressDialog.setMessage("Пожалуйста подождите...");
        mProgressDialog.setCancelable(true);

        /*
        // Set a progress dialog dismiss listener
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                // Cancel the AsyncTask
                mMyTask.cancel(false);
            }
        });
*/



        mProgressDialog.show();
        mProgressDialog.setProgress(0);
    }

    // Do the task in background/non UI thread
    protected List<Bitmap> doInBackground(URL...urls){
        int count = urls.length;
        //URL url = urls[0];
        HttpURLConnection connection = null;
        List<Bitmap> bitmaps = new ArrayList<>();

        // Loop through the urls
        for(int i=0;i<count;i++){
            URL currentURL = urls[i];
            // So download the image from this url
            try{
                // Initialize a new http url connection
                connection = (HttpURLConnection) currentURL.openConnection();

                // Connect the http url connection
                connection.connect();

                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();

                // Initialize a new BufferedInputStream from InputStream
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                // Convert BufferedInputStream to Bitmap object
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                // Add the bitmap to list
                bitmaps.add(bmp);

                // Publish the async task progress
                // Added 1, because index start from 0
                publishProgress((int) (((i+1) / (float) count) * 100));
                if(isCancelled()){
                    break;
                }

            }catch(IOException e){
                e.printStackTrace();
            }finally{
                // Disconnect the http url connection
                connection.disconnect();
            }
        }
        // Return bitmap list
        return bitmaps;
    }

    // On progress update
    protected void onProgressUpdate(Integer... progress){
        // Update the progress bar
        mProgressDialog.setProgress(progress[0]);
    }

    // On AsyncTask cancelled
    protected void onCancelled(){
        //Snackbar.make(mCLayout,"Task Cancelled.", Snackbar.LENGTH_LONG).show();
    }

    // When all async task done
    protected void onPostExecute(List<Bitmap> result){
        // Hide the progress dialog
        //mProgressDialog.dismiss();

        // Remove all views from linear layout
        // mLLayout.removeAllViews();

        // Loop through the bitmap list
        for(int i=0;i<result.size();i++){
            Bitmap bitmap = result.get(i);
            // Save the bitmap to internal storage
            Uri imageInternalUri = saveImageToInternalStorage(bitmap,i);

        }
    }

    public List<File> getImagesFromLocalStorrage(){

        List<File> images = new ArrayList<>();

        ContextWrapper wrapper = new ContextWrapper(context);
        File directory = wrapper.getDir("Images", MODE_PRIVATE);

        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            images.add(files[i]);
        }

        return images;
    }

    protected Uri saveImageToInternalStorage(Bitmap bitmap, int index){
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(context);

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images", MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, "UniqueFileName"+ index+".jpg");

        try{
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        }catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        return savedImageURI;
    }
}

