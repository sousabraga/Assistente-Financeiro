package br.com.estacio.assistentefinanceiro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.estacio.assistentefinanceiro.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
    }

    public void entrar(View view) {
        String usuarioInformado = usuario.getText().toString();
        String senhaInfomada = senha.getText().toString();

        if ("admin".equals(usuarioInformado) && "admin".equals(senhaInfomada)) {
            startActivity(new Intent(this, DashboardActivity.class));
        } else {
            String mensagemErro = "Login e/ou senha inválidos!";
            Toast toast = Toast.makeText(this, mensagemErro, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
