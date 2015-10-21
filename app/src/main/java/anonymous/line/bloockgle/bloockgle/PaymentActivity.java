package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

/**
 * Created by Mr.Marshall on 07/10/2015.
 */
public class PaymentActivity extends Activity {

//    TextView pay, filesize, coinsreceived, textonegrita, receibedbtc;
//    TextView tv1, tv2, tv3, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);

//        String font_path_1 = "font/exobold.ttf";
//        Typeface TF1 = Typeface.createFromAsset(getAssets(),font_path_1);
//        pay.setTypeface(TF1);
//        filesize.setTypeface(TF1);
//        coinsreceived.setTypeface(TF1);
//        textonegrita.setTypeface(TF1);
//        receibedbtc.setTypeface(TF1);
//
//        String font_path = "font/exoregular.ttf";
//        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);
//        tv1.setTypeface(TF);
//        tv2.setTypeface(TF);
//        tv3.setTypeface(TF);
//        address.setTypeface(TF);

        ImageButton arrawpayment = (ImageButton) findViewById(R.id.arrawpayment);
        arrawpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btncancel = (ImageButton) findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        ImageView qrCode = (ImageView) findViewById(R.id.qrCode);
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView newQrCode = new ImageView(PaymentActivity.this);
                newQrCode.setImageBitmap(getQrBitmap("asdfghjkl", 100));
                newQrCode.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(PaymentActivity.this);
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
        qrCode.setImageBitmap(getQrBitmap("asdfghjkl", 100));
        TextView pay = (TextView) findViewById(R.id.pay1);
        TextView filesize = (TextView) findViewById(R.id.filesize);
        TextView address = (TextView) findViewById(R.id.address);
        TextView coinsreceived = (TextView) findViewById(R.id.coinsreceived);

    }

    private Bitmap getQrBitmap(String address, long satoshis){
        File qrFile = QRCode.from(getBtcUri(address, satoshis)).withSize(750,750).file();
        return BitmapFactory.decodeFile(qrFile.getAbsolutePath());
    }

    private String getBtcUri(String address, long satoshis){
        String s = "bitcoin:" + address + "?amount=" + ((double) satoshis) / 1e8d + "&label=Bloockgle";
        return s;

    }
}
