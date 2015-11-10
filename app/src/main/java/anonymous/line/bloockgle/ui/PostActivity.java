package anonymous.line.bloockgle.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.chip_chap.services.asynchttp.net.ApiRequester;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anonymous.line.bloockgle.view.BorderEditText;
import anonymous.line.bloockgle.R;
import anonymous.line.bloockgle.timeline.Type;
import anonymous.line.bloockgle.handler.DialogHandler;
import anonymous.line.bloockgle.request.PublishContentRequest;

/**
 * Created by Mr.Marshall on 23/09/2015.
 */
public class PostActivity extends Activity {

    private static final String TAG = "PostActivity";
    private static final int FILE_SELECT_CODE = 1;
    private File file;
    private String fileBase64;
    private Button button;
    private String[] array;
    private ArrayList<BorderEditText> arrayList;
    private Type currentType;
    private EditText title;
    private EditText description;
    private EditText addresswallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        arrayList = new ArrayList<>();

        array = new String[Type.values().length];
        for (int x=0 ; x<array.length ; x++){
            array[x] = Type.values()[x].toUpperCase();
        }

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Select your publish type");
        alertDialog.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                currentType = Type.values()[which];
                loadView();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        addresswallet = (EditText) findViewById(R.id.addresswallet);

    }


    private void loadView(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container);
        List<String> keyword = currentType.getKeyword();
        String[] strings = new String[keyword.size()];
        keyword.toArray(strings);
        for (int x=0 ; x<strings.length ; x++){
            strings[x] = String.valueOf(strings[x].charAt(0)).toUpperCase() + strings[x].subSequence(1, strings[x].length());
            strings[x] = strings[x].replace("_", " ");
            BorderEditText borderEditText = new BorderEditText(this);
            borderEditText.setHint(strings[x]);
            borderEditText.setHintTextColor(getResources().getColor(R.color.blue));
            linearLayout.addView(borderEditText);
            arrayList.add(borderEditText);
        }

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
                final HashMap<String, String> fields = new HashMap<String, String>();
                fields.put("title", title.getText().toString());
                fields.put("description", description.getText().toString());
                fields.put("address_wallet", addresswallet.getText().toString());
                for (int x=0 ; x<arrayList.size() ; x++){
                    fields.put(currentType.getKeyword().get(x), arrayList.get(x).getText().toString());
                }

                new ApiRequester(new PublishContentRequest(fields), new DialogHandler(PostActivity.this, R.string.publish_dialopg_title, R.string.publish_dialog_message) {
                    @Override
                    public void onOkResponse(JSONObject jsonObject) throws JSONException {
                        String address = jsonObject.getString("address");
                        double price = jsonObject.getDouble("price");
                        Intent intent = new Intent(PostActivity.this, PaymentActivity.class);
                        intent.putExtra("address", address);
                        intent.putExtra("price", price);
                        intent.putExtra("data", fields);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        Log.e(TAG, errorResponse);
                    }
                }).execute();

            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

//    @Override
//    protected void onActivityResult (int requestCode,int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == FILE_SELECT_CODE){
//            Uri uri = data.getData();
//            try {
//                String path = Utils.getPath(this, uri);
//                file = new File(path);
//                loadFile();
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void loadFile () {
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            byte[] arrayByte = new byte[4096];
//            int read = 0;
//            while ((read = fileInputStream.read(arrayByte)) != -1){
//                byteArrayOutputStream.write(arrayByte, 0, read);
//            }
//            fileInputStream.close();
//            byteArrayOutputStream.close();
//            fileBase64 = Utils.encode(byteArrayOutputStream.toByteArray());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



}
