package ru.tablet.assistant;
import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.speech.RecognizerIntent;
import android.widget.*;
import java.util.ArrayList;
public class DictationActivity extends Activity {
 public void onCreate(Bundle state) {
  super.onCreate(state);
  if(state!=null) {finish(); return;}
  Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
  i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
  i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ru-RU");
  i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Продиктуйте текст");
  try {startActivityForResult(i,1);} catch(ActivityNotFoundException e) {Toast.makeText(this,"Нет сервиса распознавания речи. Установите или включите приложение Google.",Toast.LENGTH_LONG).show(); finish();}
 }
 protected void onActivityResult(int request,int result,Intent data) {
  super.onActivityResult(request,result,data);
  if(request!=1 || result!=RESULT_OK || data==null) {finish(); return;}
  ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
  if(results==null || results.isEmpty()) {finish();return;}
  LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(24,24,24,24);
  TextView warning=new TextView(this); warning.setText("Текст заменит всё содержимое выбранного поля:"); l.addView(warning);
  EditText text=new EditText(this); text.setText(results.get(0)); l.addView(text);
  Button insert=new Button(this); insert.setText("Вставить"); l.addView(insert);
  insert.setOnClickListener(v-> {InputService s=InputService.instance; boolean ok=s!=null && s.insert(text.getText().toString()); Toast.makeText(this,ok ? "Текст вставлен" : "Не удалось подтвердить вставку. Проверьте поле.",Toast.LENGTH_LONG).show(); finish();});
  Button cancel=new Button(this); cancel.setText("Отмена"); cancel.setOnClickListener(v->finish()); l.addView(cancel);
  setContentView(l);
 }
}