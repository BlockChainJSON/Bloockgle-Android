package anonymous.line.bloockgle.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import anonymous.line.bloockgle.R;
import anonymous.line.bloockgle.util.PaymentReference;
import anonymous.line.bloockgle.util.Utils;

public class PublishedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);


        Bundle extras = getIntent().getExtras();
        PaymentReference paymentReference = (PaymentReference) extras.getSerializable("payment");

        if (paymentReference != null) {
            showContent(paymentReference);
        }
    }

    private void showContent(PaymentReference paymentReference) {
        LinearLayout detalLayout = (LinearLayout) findViewById(R.id.detail_layout);
        TextView reference = (TextView) findViewById(R.id.reference);
        reference.setText(paymentReference.getReference());

        for (final String hash : paymentReference.getTransactionHashes()) {
            TextView t = new TextView(this);
            t.setTextColor(getResources().getColor(R.color.text_effect_address));
            t.setText(hash);
            t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            t.setPadding(Utils.getPixels(this, 10), 0, Utils.getPixels(this, 10), 0);

            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setText(hash);
                    } else {
                        ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Transaction hash", hash);
                        clipboard.setPrimaryClip(clip);
                    }
                    Toast.makeText(PublishedActivity.this, getString(R.string.tx_hash_copied), Toast.LENGTH_SHORT).show();
                }
            });
            detalLayout.addView(t);
        }
    }

}
