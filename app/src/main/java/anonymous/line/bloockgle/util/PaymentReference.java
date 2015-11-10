package anonymous.line.bloockgle.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ander on 10/11/15.
 */
public class PaymentReference implements Serializable {

    private static final long serialVersionUID = -1853315050566368106L;
    private List<String> transactionHashes;
    private String reference;

    public PaymentReference(JSONObject jsonObject) throws JSONException {
        JSONObject data  = new JSONObject(jsonObject.getString("transactions"));
        JSONArray transactions = data.getJSONArray("txids");

        transactionHashes = new ArrayList<>();
        for (int x  = 0; x < transactions.length(); x++) {
            String txHash = transactions.getString(x);
            transactionHashes.add(txHash);
        }

        this.reference = data.getString("ref");
    }

    public List<String> getTransactionHashes() {
        return transactionHashes;
    }

    public String getReference() {
        return reference;
    }
}

        /*
    {
    "payment":"ok",
    "btc":0.0004,
    "price":0.0004,
    "transactions":"
        {\"txids\":[
            \"df8e156a5c5700e6d9c53fec3eb8a4420011179351c89a6a506ef395fb3eb16a\",
            \"f1a8658e6368c9325f9cf9f34af942c1b240ee1bbe1082f3be23442e27b50324\",
            \"7219c7d35b071390a59dd119b214b9843e08a7c09368cdc03bc279cbec11ca19\",
            \"7127ab793dae2660919ced6cab6c0c9aff6bfbca4c5331b0900dadac94e8f8e0\"],
        \"ref\":\"382943-036575\"}",
    "index":"{
        \"txids\":[
            \"3db89ed05d6b969eef0463657ee67306dbf7f386cfd71b4bf34e5ff015e0cd40\"],
            \"ref\":\"382943-047165\"}"}
     */