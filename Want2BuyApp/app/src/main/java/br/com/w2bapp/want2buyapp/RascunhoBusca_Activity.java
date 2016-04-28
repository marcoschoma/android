package br.com.w2bapp.want2buyapp;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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
    }
}
