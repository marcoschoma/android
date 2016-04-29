package br.com.w2bapp.want2buyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RascunhoBusca_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rascunho_busca_);

        final AutoCompleteTextView txtPesquisa = (AutoCompleteTextView) findViewById(R.id.txtPesquisa);
        final Button btnLimparPesquisa = (Button) findViewById(R.id.btnLimparPesquisa);

        txtPesquisa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    //txtPesquisa.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.));

                }
            }
        });

        txtPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtPesquisa.getText().toString().equalsIgnoreCase(""))
                {
                    btnLimparPesquisa.setVisibility(View.INVISIBLE);
                } else {
                    btnLimparPesquisa.setVisibility(View.VISIBLE);
                }
            }
        });

        btnLimparPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPesquisa.setText("");
                btnLimparPesquisa.setVisibility(View.INVISIBLE);
            }
        });

        Button btnNotificar = (Button) findViewById(R.id.btnNotificar);
        btnNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    //new HttpGetTask(RascunhoBusca_Activity.this, "http://localhost:59557/api/values").execute();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
                //if(PermissionChecker.checkPermission(RascunhoBusca_Activity.this, ))

                RequestQueue requestQueue = Volley.newRequestQueue(RascunhoBusca_Activity.this);
                String url = "http://localhost:59557/api/values";
                StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RascunhoBusca_Activity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RascunhoBusca_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });
    }
}
