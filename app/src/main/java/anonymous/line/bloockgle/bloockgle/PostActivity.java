package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Mr.Marshall on 23/09/2015.
 */
public class PostActivity extends Activity {

    private static final int FILE_SELECT_CODE = 1;
    private File file;
    private String fileBase64;
    private Button button;
    private FileItemAdapter fileItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        ImageButton btnatras = (ImageButton) findViewById(R.id.btnatras);
        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton btnadelante = (ImageButton) findViewById(R.id.btnadelante);
        btnadelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<File> arrayList = new ArrayList<>();
        fileItemAdapter = new FileItemAdapter(this, R.layout.file_item_row, arrayList);
        listView.setAdapter(fileItemAdapter);
        button = (Button) findViewById(R.id.addfile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Choose file..."),FILE_SELECT_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult (int requestCode,int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == FILE_SELECT_CODE){
            Uri uri = data.getData();
            try {
                String path = Utils.getPath(this, uri);
                file = new File(path);
                loadFile();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFile () {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] arrayByte = new byte[4096];
            int read = 0;
            while ((read = fileInputStream.read(arrayByte)) != -1){
                byteArrayOutputStream.write(arrayByte, 0, read);
            }
            fileInputStream.close();
            byteArrayOutputStream.close();
            fileBase64 = Utils.encode(byteArrayOutputStream.toByteArray());
            fileItemAdapter.add(file);
            fileItemAdapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
