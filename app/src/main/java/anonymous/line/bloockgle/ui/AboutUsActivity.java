package anonymous.line.bloockgle.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

import anonymous.line.bloockgle.R;

/**
 * Created by Mr.Marshall on 23/09/2015.
 */
public class AboutUsActivity extends Activity {

    private static final String BTC_ADDRESS = "1RhT33BF9krL6mR8xxux4ir957bZCzfws";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_as_activity);

        ImageButton arrawpayment = (ImageButton) findViewById(R.id.arrawpayment);
        arrawpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button donate = (Button) findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView newQrCode = new ImageView(AboutUsActivity.this);
                newQrCode.setImageBitmap(getQrBitmap());
                newQrCode.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(AboutUsActivity.this);
                alertDialogBuider.setView(newQrCode);
                alertDialogBuider.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuider.setNeutralButton(R.string.donate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        launchBtcIntent();
                    }
                });
                alertDialogBuider.setNegativeButton(R.string.copy_address, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText(BTC_ADDRESS);
                        } else {
                            ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Bloockgle address", BTC_ADDRESS);
                            clipboard.setPrimaryClip(clip);
                        }
                        Toast.makeText(AboutUsActivity.this, getString(R.string.btc_address_copied), Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuider.show();
            }
        });
    }

    private void launchBtcIntent() {
        Intent launchBtcApp = new Intent(Intent.ACTION_VIEW);
        launchBtcApp.setData(Uri.parse(createUri(BTC_ADDRESS)));
        try {
            startActivity(launchBtcApp);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Bitcoin wallet not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getQrBitmap(){
        File qrFile = QRCode.from(createUri(BTC_ADDRESS)).withSize(750,750).file();
        return BitmapFactory.decodeFile(qrFile.getAbsolutePath());
    }

    private String createUri(String address){
        return "bitcoin:" + address;

    }
}
