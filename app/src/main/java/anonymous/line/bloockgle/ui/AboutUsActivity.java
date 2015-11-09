package anonymous.line.bloockgle.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

import anonymous.line.bloockgle.bloockgle.R;

/**
 * Created by Mr.Marshall on 23/09/2015.
 */
public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_as_activity);

        ImageButton arrawpayment = (ImageButton) findViewById(R.id.arrawpayment);
        arrawpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnpublish = (ImageButton) findViewById(R.id.btnpublish);
        btnpublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        Button donate = (Button) findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView newQrCode = new ImageView(AboutUsActivity.this);
                newQrCode.setImageBitmap(getQrBitmap("asdfghjkl"));
                newQrCode.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(AboutUsActivity.this);
                alertDialogBuider.setView(newQrCode);
                alertDialogBuider.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuider.show();
            }
        });
    }

    private Bitmap getQrBitmap(String address){
        File qrFile = QRCode.from(getBtcUri(address)).withSize(750,750).file();
        return BitmapFactory.decodeFile(qrFile.getAbsolutePath());
    }

    private String getBtcUri(String address){
        String s = "bitcoin:" + address + "&label=Bloockgle";
        return s;

    }
}
