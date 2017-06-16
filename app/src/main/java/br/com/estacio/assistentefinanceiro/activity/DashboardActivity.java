package br.com.estacio.assistentefinanceiro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import br.com.estacio.assistentefinanceiro.R;
import br.com.estacio.assistentefinanceiro.dao.LancamentoDAO;

public class DashboardActivity extends AppCompatActivity {

    TextView totalReceitas;
    TextView totalDespesas;
    TextView totalInvestimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        totalReceitas = (TextView) findViewById(R.id.totalReceitas);
        totalDespesas = (TextView) findViewById(R.id.totalDespesas);
        totalInvestimentos = (TextView) findViewById(R.id.totalInvestimentos);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LancamentoDAO lancamentoDAO = new LancamentoDAO(getBaseContext());

        totalReceitas.setText(String.valueOf("R$ " + lancamentoDAO.selectTotalLancamentos("Receitas")));
        totalDespesas.setText(String.valueOf("R$ " + lancamentoDAO.selectTotalLancamentos("Despesas")));
        totalInvestimentos.setText(String.valueOf("R$ " + lancamentoDAO.selectTotalLancamentos("Investimentos")));
    }

    public void novoLancamento(View view) {
        startActivity(new Intent(this, CadastroLancamentoActivity.class));
    }

    public void listarLancamentos(View view) {
        startActivity(new Intent(this, LancamentosActivity.class));
    }

}
