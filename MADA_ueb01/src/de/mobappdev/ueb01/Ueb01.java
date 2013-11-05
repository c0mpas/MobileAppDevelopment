package de.mobappdev.ueb01;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobappdev.R;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class Ueb01 extends Activity {

	// Calc Klasse
	private Calculable calc;
	
	// Klasse zum auslesen der aktuellen Displaykonfiguration
	Configuration conf;
	
	// Klassen fuer grafische Oberfl‰che deklarieren
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
	private TextView txtMul;
	private TextView txtMinus;
	private TextView txtDiv;
	
	private TextView txtLpar;
	private TextView txtRpar;
	private TextView txtMod;
	private TextView txtPow;
	
	private TextView txtSin;
	private TextView txtCos;
	private TextView txtTan;
	private TextView txtSqrt;
	
	private TextView txtPoint;
	private TextView txtEquals;
	private ImageView backImg;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_ueb01);
		conf = getResources().getConfiguration();

		referenceViews();
		setMainListeners();
		
		switch (conf.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
			case Configuration.SCREENLAYOUT_SIZE_SMALL:		inflateAdditionalMenu();
															break;
			case Configuration.SCREENLAYOUT_SIZE_NORMAL:	inflateAdditionalMenu();
															break;
			case Configuration.SCREENLAYOUT_SIZE_LARGE:		setAdditionalListeners();
															break;
			case Configuration.SCREENLAYOUT_SIZE_XLARGE:	setAdditionalListeners();
															break;
			default :										inflateAdditionalMenu();
															break;
		}
	}


	private OnClickListener generalButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
    		switch (v.getId()) {
	    		case R.id.ueb01_1:		addToCalcField(getString(R.string.ueb01_1));
	    								break;
	    		case R.id.ueb01_2:		addToCalcField(getString(R.string.ueb01_2));
										break;
	    		case R.id.ueb01_3:		addToCalcField(getString(R.string.ueb01_3));
										break;
	    		case R.id.ueb01_4:		addToCalcField(getString(R.string.ueb01_4));
										break;
	    		case R.id.ueb01_5:		addToCalcField(getString(R.string.ueb01_5));
										break;
	    		case R.id.ueb01_6:		addToCalcField(getString(R.string.ueb01_6));
										break;
	    		case R.id.ueb01_7:		addToCalcField(getString(R.string.ueb01_7));
										break;
	    		case R.id.ueb01_8:		addToCalcField(getString(R.string.ueb01_8));
										break;
	    		case R.id.ueb01_9:		addToCalcField(getString(R.string.ueb01_9));
										break;
	    		case R.id.ueb01_0:		addToCalcField(getString(R.string.ueb01_0));
										break;
	    		case R.id.ueb01_minus:	addToCalcField(getString(R.string.ueb01_minus));
										break;
	    		case R.id.ueb01_plus:	addToCalcField(getString(R.string.ueb01_plus));
										break;
	    		case R.id.ueb01_mul:	addToCalcField(getString(R.string.ueb01_mul));
										break;
	    		case R.id.ueb01_divide:	addToCalcField(getString(R.string.ueb01_divide));
										break;
	    		case R.id.ueb01_lpar:	addToCalcField(getString(R.string.ueb01_lpar));
										break;
	    		case R.id.ueb01_rpar:	addToCalcField(getString(R.string.ueb01_rpar));
										break;
	    		case R.id.ueb01_mod:	addToCalcField(getString(R.string.ueb01_mod));
										break;
	    		case R.id.ueb01_pow:	addToCalcField(getString(R.string.ueb01_pow));
										break;
	    		default:				// Do nothing
										break;
	    	}
		}
	};
	
	
	private OnClickListener extendedButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
    		/*
    		switch (v.getId()) {
	    		case R.id.ueb01_sin:	addToCalcField(getString(R.string.ueb01_sin));
	    								break;
	    		case R.id.ueb01_cos:	addToCalcField(getString(R.string.ueb01_cos));
										break;
	    		case R.id.ueb01_tan:	addToCalcField(getString(R.string.ueb01_tan));
										break;
	    		case R.id.ueb01_sqrt:	addToCalcField(getString(R.string.ueb01_sqrt));
										break;
	    		default:				// Do nothing
										break;
	    	}
	    	*/
    		addToCalcField(getString(R.string.ueb01_lpar));
		}
	};
	
	
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
		txtMul = (TextView) findViewById(R.id.ueb01_mul);
		txtDiv = (TextView) findViewById(R.id.ueb01_divide);
		txtLpar = (TextView) findViewById(R.id.ueb01_lpar);
		txtRpar = (TextView) findViewById(R.id.ueb01_rpar);
		txtMod = (TextView) findViewById(R.id.ueb01_mod);
		txtPow = (TextView) findViewById(R.id.ueb01_pow);
		txtPoint = (TextView) findViewById(R.id.ueb01_komma);
		txtEquals = (TextView) findViewById(R.id.ueb01_equals);
		backImg = (ImageView) findViewById(R.id.ueb01_backBT);
	}


	private void inflateAdditionalMenu() {
		// ##########
	}
	
	
	/**
	 * Setzt die Listener, die immer da sind
	 */
	private void setMainListeners() {

		txt1.setOnClickListener(generalButtonListener);
		txt2.setOnClickListener(generalButtonListener);
		txt3.setOnClickListener(generalButtonListener);
		txt4.setOnClickListener(generalButtonListener);
		txt5.setOnClickListener(generalButtonListener);
		txt6.setOnClickListener(generalButtonListener);
		txt7.setOnClickListener(generalButtonListener);
		txt8.setOnClickListener(generalButtonListener);
		txt9.setOnClickListener(generalButtonListener);
		txt0.setOnClickListener(generalButtonListener);
		
		txtMinus.setOnClickListener(generalButtonListener);
		txtPlus.setOnClickListener(generalButtonListener);
		txtMul.setOnClickListener(generalButtonListener);
		txtDiv.setOnClickListener(generalButtonListener);
		txtLpar.setOnClickListener(generalButtonListener);
		txtRpar.setOnClickListener(generalButtonListener);
		txtMod.setOnClickListener(generalButtonListener);
		txtPow.setOnClickListener(generalButtonListener);
		
		txtPoint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (calcField.length() < 1) {
					addToCalcField(getString(R.string.ueb01_0) + getString(R.string.ueb01_komma));
				} else {
					addToCalcField(getString(R.string.ueb01_komma));
				}
			}
		});

		backImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
					calc = new ExpressionBuilder(calcField.getText().toString().replace(getString(R.string.ueb01_mul), 
							getString(R.string.ueb01_mul_calc))).build();
					Double result = calc.calculate();
					// Schreibt das Ergebnis in das Ergebnis-Textfeld
					calcField.setText(result.toString());
				} catch (Exception e) {
					// Abfangen eventueller Exceptions
					Toast.makeText(getApplicationContext(), getString(R.string.ueb01_wrongInput), Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});

	}
	
	
	/**
	 * Setzt zusaetzliche Listener fuer Geraete mit groﬂen Displays
	 */
	private void setAdditionalListeners() {
		txtSin.setOnClickListener(extendedButtonListener);
		txtCos.setOnClickListener(extendedButtonListener);
		txtTan.setOnClickListener(extendedButtonListener);
		txtSqrt.setOnClickListener(extendedButtonListener);
	}
	
	
	/**
	 * H‰ngt den Wert der gedrueckten Taste an das Ergebnisfeld an.
	 */
	private void addToCalcField(String txt) {
		calcField.setText(calcField.getText() + txt);
	}
	

	/**
	 * Beendet die Anwendung.
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
