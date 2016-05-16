package com.example.pokependu;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

public class MainActivity extends Activity {
	LinearLayout layout;
	ImageView image;
	TextView wordText;
	TextView entWordsText;
	EditText editText;
	Word word;
	MediaPlayer mPlayer;
	int trials = 6; // Remaining number of trials
	boolean ingame = true;
	boolean anim = false;
	long clicTime = 0;
	
	String words[] = { "Bulbizarre", "Herbizarre", "Florizarre", "Salamèche", "Reptincel", "Dracaufeu", "Carapuce", "Carabaffe", "Tortank", "Chenipan", "Chrysacier", "Papilusion", "Aspicot", "Coconfort", "Dardargnan", "Roucool", "Roucoups", "Roucarnage", "Rattata", "Rattatac", "Piafabec", "Rapasdepic", "Abo", "Arbok", "Pikachu", "Raichu", "Sabelette", "Sablaireau", "Nidoran", "Nidorina", "Nidoqueen", "Nidorino", "Nidoking", "Mélofée", "Mélodelfe", "Goupix", "Feunard", "Rondoudou", "Grodoudou", "Nosferapti", "Nosferalto", "Mystherbe", "Ortide", "Rafflésia", "Paras", "Parasect", "Mimitoss", "Aéromite", "Taupiqueur", "Triopikeur", "Miaouss", "Persian", "Psykokwak", "Akwakwak", "Férosinge", "Colossinge", "Caninos", "Arcanin", "Ptitard", "Têtarte", "Tartard", "Abra", "Kadabra", "Alakazam", "Machoc", "Machopeur", "Mackogneur", "Chétiflor", "Boustiflor", "Empiflor", "Tentacool", "Tentacruel", "Racaillou", "Gravalanch", "Grolem", "Ponyta", "Galopa", "Ramoloss", "Flagadoss", "Magnéti", "Magnéton", "Canarticho", "Doduo", "Dodrio", "Otaria", "Lamantine", "Tadmorv", "Grotadmorv", "Kokiyas", "Crustabri", "Fantominus", "Spectrum", "Ectoplasma", "Onix", "Soporifik", "Hypnomade", "Krabby", "Krabboss", "Voltorbe", "Électrode", "Nœunœuf", "Noadkoko", "Osselait", "Ossatueur", "Kicklee", "Tygnon", "Excelangue", "Smogo", "Smogogo", "Rhinocorne", "Rhinoféros", "Leveinard", "Saquedeneu", "Kangourex", "Hypotrempe", "Hypocéan", "Poissirène", "Poissoroy", "Stari", "Staross", "M. Mime", "Insécateur", "Lippoutou", "Élektek", "Magmar", "Scarabrute", "Tauros", "Magicarpe", "Léviator", "Lokhlass", "Métamorph", "Évoli", "Aquali", "Voltali", "Pyroli", "Porygon", "Amonita", "Amonistar", "Kabuto", "Kabutops", "Ptéra", "Ronflex", "Artikodin", "Électhor", "Sulfura", "Minidraco", "Draco", "Dracolosse", "Mewtwo", "Mew" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layout = (LinearLayout) findViewById(R.id.mainlayout);
		image = (ImageView) findViewById(R.id.image);
		wordText = (TextView) findViewById(R.id.word);
		entWordsText = (TextView) findViewById(R.id.entWords);
		editText = (EditText) findViewById(R.id.editText);
		
		word = new Word();
		word.selectWord(words[(int) (Math.random() * (words.length - 1))]);

		anim = true;

		// Start animation
		for (int i = 1; i < 7; i++) {
			final int file = 7 - i;

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					changePicture("pendu_" + file);

					if (file == 1) {
						editText.setEnabled(true);
						anim = false;
					}
				}
			}, i*300);
		}

		editText.setOnKeyListener(new OnKeyListener() {        
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
					String enteredWord = editText.getText().toString().toUpperCase();
					
					if (!enteredWord.equals("")) {
						entWordsText.setText(entWordsText.getText() + " " + enteredWord);
						
						if (!word.testWord(enteredWord)) {
							trials--;
							changePicture("pendu_" + (7 - trials));
			
							if (trials == 0) { // Try again!
								loose();
							}
						} else {
							word.addEntry(enteredWord);
							showWord(word.getFoundLettersArray());
							
							if (word.winTest()) {
								win();
							}
						}
					}
					
					editText.setText("");
				}
				return false;
			}
		});

		entWordsText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((!anim) && ((SystemClock.uptimeMillis() - clicTime) < 300)) {
					showWord(word.getWordArray());
					win();
				} else if (!ingame) {
					newGame();
				}
				clicTime = SystemClock.uptimeMillis();
		    }
		});

		wordText.setOnClickListener(new clicListener());
		image.setOnClickListener(new clicListener());
		layout.setOnClickListener(new clicListener());
	}
	
	public class clicListener implements OnClickListener {
		public void onClick(View v) {
			if (!ingame) {
				newGame();
			}
	    }
	}
	
	public void newGame() {
		trials = 6;
		changePicture("pendu_1");
		entWordsText.setText("Mots entrés :");
		word.selectWord(words[(int) (Math.random() * (words.length - 1))]);
		showWord(word.getFoundLettersArray());
		editText.setEnabled(true);
		ingame = true;
	}
	
	public void win() {
		editText.setEnabled(false);
		
		mPlayer = MediaPlayer.create(MainActivity.this, R.raw.win);
        mPlayer.start();

		long time = SystemClock.uptimeMillis();
		anim = true;

		for (int i = 0; i < 44; i++) {
			final int file = i%4 + 1;
			final boolean end = (i == 43) ? true : false;

			new Handler().postAtTime(new Runnable() {
				@Override
				public void run() {
					changePicture("win_" + file);
					
					if (end) {
						ingame = false;
						anim = false;
					}
				}
			}, time + 150*i);
		}
	}
	
	public void loose() {
		editText.setEnabled(false);
		showWord(word.getWordArray());
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				changePicture("loose");
				ingame = false;
			}
		}, 1500);
	}
	
	public void showWord(char[] array) {
		String str = new String (" Mot : ");
		
		for (int i = 0; i < array.length; i++) {
			str += array[i] + " ";
		}

		wordText.setText(str);
	}
	
	public void changePicture(String str) {
		Drawable draw = getResources().getDrawable(getResources().getIdentifier("@drawable/" + str, null, getPackageName()));
		image.setImageDrawable(draw);
	}
}
