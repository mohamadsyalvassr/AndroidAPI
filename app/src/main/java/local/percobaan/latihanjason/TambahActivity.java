package local.percobaan.latihanjason;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import local.percobaan.latihanjason.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahActivity extends AppCompatActivity implements View.OnClickListener {

    koneksi koneksi = new koneksi();
    ProgressDialog pDialog;
    Button btnSimpan;
    EditText txtNama,txtNim,txtAlamat;
    Intent intent;
    ConnectivityManager conMgr;
    private String url = koneksi.isi_koneksi() + "tambah.php";
    private static final String TAG = TambahActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()){

            }else{
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        txtNama = (EditText) findViewById(R.id.txtNama);
        txtNim = (EditText) findViewById(R.id.txtNim);
        txtAlamat = (EditText) findViewById(R.id.txtAlamat);
        btnSimpan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String nama = txtNama.getText().toString();
        String nim = txtNim.getText().toString();
        String alamat = txtAlamat.getText().toString();
        if (nama.trim().length() > 0){
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()){
                checkRegister(nama,nim,alamat);
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Kolom Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }
    }

    private void checkRegister(final String nama, final String nim, final String alamat){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Simpan ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response : "+ response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");

                    if (success.equals("1")) {
                        Log.e("Successfully Save!", jObj.toString());

                        Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();

                        txtNama.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Gagal Disimpan", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Simpan Data Error : "+ error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("nim", nim);
                params.put("namamahasiswa",nama);
                params.put("alamat", alamat);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showDialog(){
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog(){
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
