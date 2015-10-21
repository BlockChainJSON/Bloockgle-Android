package anonymous.line.bloockgle.bloockgle;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Mr.Marshall on 02/10/2015.
 */
public class Utils {

    public static Drawable GetBitmapDrawable (byte[] bytes){

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return BitmapDrawable.createFromStream(bais, "img");
    }

    public static File decodeFile (byte[] bytes, Context context) {
        String rootDirectory = context.getFilesDir().getAbsolutePath() + "/";
        rootDirectory = rootDirectory + System.currentTimeMillis();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(rootDirectory);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            return new File(rootDirectory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static File decodeFile (String base64File, Context context) {
        byte[] imageBytes = Base64.decode(base64File, Base64.DEFAULT);
        return decodeFile(imageBytes, context);
    }

    public static Drawable GetBitmapDrawable (String base64Imagen){

        byte[] imageBytes = Base64.decode(base64Imagen, Base64.DEFAULT);
        return GetBitmapDrawable(imageBytes);

    }

    public byte[] getBitmapBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return  stream.toByteArray();
    }

    private Bitmap decodeUriBitmap(Uri selectedImage,Context context) throws FileNotFoundException {

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 140;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o2);

    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String encode (byte[] arrayByte){
        return Base64.encodeToString(arrayByte, Base64.DEFAULT);
    }

}
