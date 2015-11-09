package anonymous.line.bloockgle.bloockgle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chip_chap.services.cash.coin.BitCoin;

import net.glxn.qrgen.android.QRCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Mr.Marshall on 07/10/2015.
 */
public class PaymentActivity extends Activity implements CheckLauncher.CheckHolder {

//    TextView pay, filesize, coinsreceived, textonegrita, receibedbtc;
//    TextView tv1, tv2, tv3, address;

    private String address;
    private double price;
    private HashMap<String, String> data;
    private TextView coinsreceived;
    private CheckLauncher checkLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);

        Bundle bundle = getIntent().getExtras();
        address = bundle.getString("address");
        price = bundle.getDouble("price");
        data = (HashMap<String, String>) bundle.getSerializable("data");
        checkLauncher = new CheckLauncher(new CheckApiRequest(data, address), PaymentActivity.this);
        checkLauncher.start();

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
                finish();
            }
        });
        final BitCoin priceBtc = BitCoin.valueOf(price);

        ImageButton btncancel = (ImageButton) findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView qrCode = (ImageView) findViewById(R.id.qrCode);
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView newQrCode = new ImageView(PaymentActivity.this);
                newQrCode.setImageBitmap(getQrBitmap());
                newQrCode.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(PaymentActivity.this);
                alertDialogBuider.setView(newQrCode);
                alertDialogBuider.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuider.setNeutralButton("Pay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        launchBtcIntent(priceBtc.getDoubleValue());
                    }
                });
                alertDialogBuider.show();
            }
        });
        qrCode.setImageBitmap(getQrBitmap());
        TextView pay = (TextView) findViewById(R.id.pay1);

        pay.setText(priceBtc.toFriendlyString());

        TextView contentsize = (TextView) findViewById(R.id.contentsize);
        contentsize.setText(getContentSize() + "Kb");
        TextView address = (TextView) findViewById(R.id.address);
        address.setText(this.address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchBtcIntent(priceBtc.getDoubleValue());
            }
        });
        coinsreceived = (TextView) findViewById(R.id.coinsreceived);
        coinsreceived.setText(String.format(
                "%1$s / %2$s", BitCoin.valueOf(0L).toFriendlyString(), priceBtc.toFriendlyString()));

    }

    private void launchBtcIntent(double amount) {
        Intent launchBtcApp = new Intent(Intent.ACTION_VIEW);
        launchBtcApp.setData(Uri.parse(createUri(this.address, amount)));
        try {
            startActivity(launchBtcApp);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Bitcoin wallet not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public int getContentSize(){
        int transactions = (int) Math.ceil(price/0.001);
        return transactions*40;
    }

    public String createUri(String address, double price){
        return "bitcoin:" + address + "?amount=" + price + "&label=Bloockgle";
    }

    private Bitmap getQrBitmap(){
        File qrFile = QRCode.from(createUri(address, price)).withSize(750,750).file();
        return BitmapFactory.decodeFile(qrFile.getAbsolutePath());
    }

    @Override
    public void check(JSONObject jsonObject) throws JSONException {
        long received = jsonObject.getLong("btc");
        String paymentStatus = jsonObject.getString("payment");
        coinsreceived.setText(String.format(
                "%1$s / %2$s", BitCoin.valueOf(received).toFriendlyString(), BitCoin.valueOf(price).toFriendlyString()));
        if (paymentStatus.equals("ok")){
            checkLauncher.cancel();
        }
    }
}
