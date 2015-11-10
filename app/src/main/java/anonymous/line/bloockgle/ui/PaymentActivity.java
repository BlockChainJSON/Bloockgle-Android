package anonymous.line.bloockgle.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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

import anonymous.line.bloockgle.timeline.CheckLauncher;
import anonymous.line.bloockgle.R;
import anonymous.line.bloockgle.request.CheckApiRequest;

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
        TextView pay = (TextView) findViewById(R.id.to_pay);
        pay.setText(getPayString(priceBtc.toFriendlyString(), getContentSize() + "Kb"));

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

    private Spanned getPayString(String amount, String size) {
        String res = String.format(getString(R.string.you_have_to_pay), amount, size);

        return Html.fromHtml(res);
    }
    @Override
    public void onPause() {
        checkLauncher.cancel();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLauncher = new CheckLauncher(new CheckApiRequest(data, address), PaymentActivity.this);
        checkLauncher.start();
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
            Toast.makeText(this, R.string.published, Toast.LENGTH_SHORT).show();
        }
    }
}
