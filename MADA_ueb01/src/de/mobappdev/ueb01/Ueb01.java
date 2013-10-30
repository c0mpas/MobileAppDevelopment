package de.mobappdev.ueb01;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobappdev.R;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class Ueb01 extends Activity {

	// Calc Klasse
	private Calculable calc;
	
	// Klassen fuer grafische Oberfläche deklarieren
	private TextView calcField;
	private TextView txt1;
	private TextView txt2;
	private TextView txt3;
	private TextView txt4;
	private TextView txt5;
	private TextView txt6;
	private TextView txt7;
	private TextView txt8;
	private TextView txt9;
	private TextView txt0;
	private TextView txtPlus;
	private TextView txtMultipli;
	private TextView txtMinus;
	private TextView txtDivide;
	private TextView txtPoint;
	private TextView txtEquals;
	private ImageView backImg;
	
	// Fuer die Reaktion auf fehlerhafte Eingaben
	private boolean error;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//getActionBar().setDisplayShowHomeEnabled(false);
		setTitle(getString(R.string.app_title));
		
		setContentView(R.layout.activity_ueb01);

		referenceViews();
		setListeners();
		
		error = false;
	}

	
	/**
	 *  Referenziert die Java-Variablen mit dem entsprechendem Objekt in der XML-Datei
	 */
	private void referenceViews() {

		calcField = (TextView) findViewById(R.id.ueb01_calcTextField);
		txt1 = (TextView) findViewById(R.id.ueb01_1);
		txt2 = (TextView) findViewById(R.id.ueb01_2);
		txt3 = (TextView) findViewById(R.id.ueb01_3);
		txt4 = (TextView) findViewById(R.id.ueb01_4);
		txt5 = (TextView) findViewById(R.id.ueb01_5);
		txt6 = (TextView) findViewById(R.id.ueb01_6);
		txt7 = (TextView) findViewById(R.id.ueb01_7);
		txt8 = (TextView) findViewById(R.id.ueb01_8);
		txt9 = (TextView) findViewById(R.id.ueb01_9);
		txt0 = (TextView) findViewById(R.id.ueb01_0);
		txtMinus = (TextView) findViewById(R.id.ueb01_minus);
		txtPlus = (TextView) findViewById(R.id.ueb01_plus);
		txtMultipli = (TextView) findViewById(R.id.ueb01_x);
		txtDivide = (TextView) findViewById(R.id.ueb01_divide);
		txtPoint = (TextView) findViewById(R.id.ueb01_komma);
		txtEquals = (TextView) findViewById(R.id.ueb01_equals);
		backImg = (ImageView) findViewById(R.id.ueb01_backBT);

	}

	/**
	 * Setzt Listener, die auf Knopfdruck reagieren
	 * Gibt den Buttons ihre Funktion
	 */
	private void setListeners() {

		txt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_1));
			}
		});
		
		txt2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_2));
			}
		});
		
		txt3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_3));
			}
		});
		
		txt4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_4));
			}
		});
		
		txt5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_5));
			}
		});
		
		txt6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_6));
			}
		});
		
		txt7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_7));
			}
		});
		
		txt8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_8));
			}
		});
		
		txt9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_9));
			}
		});
		
		txt0.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_0));
			}
		});
		
		txtMinus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_minus));
			}
		});
		
		txtPlus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_plus));
			}
		});
		
		txtMultipli.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_x));
			}
		});
		
		txtDivide.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_divide));
			}
		});
		
		txtPoint.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addToCalcField(getString(R.string.ueb01_komma));
			}
		});

		backImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				clearCalcFieldAfterError();
				
				if (calcField.length() > 0) {
					calcField.setText(calcField.getText().subSequence(0, calcField.length() - 1));
				}
					
			}
		});
		
		backImg.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				// Leert das Ergebnis-TextFeld 
				calcField.setText("");
				return false;
			}
		});

		txtEquals.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					// Uebergibt den mathematischen Ausdruck der Calc-Library 
					calc = new ExpressionBuilder(calcField.getText().toString()
								.replace(getString(R.string.ueb01_x), getString(R.string.ueb01_x_calc))).build();
					Double result = calc.calculate();
					
					// Schreibt das Ergebnis in das Ergebnis-Textfeld
					calcField.setText(result.toString());
					
				} catch (Exception e) {
					// Abfangen eventueller Exceptions
					calcField.setText(getString(R.string.ueb01_wrongInput));
					error = true;
					e.printStackTrace();

				}
			}
		});

	}
	
	
	/**
	 * Hängt den Wert der gedrueckten Taste an das Ergebnisfeld an.
	 */
	private void addToCalcField(String txt) {
		clearCalcFieldAfterError();
		calcField.setText(calcField.getText() + txt);
	}
	
	/**
	 * Leert das Ergebnisfeld wenn zuvor ein Fehler angezeigt wurde.
	 */
	private void clearCalcFieldAfterError() {
		if (error) {
			calcField.setText("");
			error = false;
		}
	}


	/**
	 * Beendet die Anwendung.
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
