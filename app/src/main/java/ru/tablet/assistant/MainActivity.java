package ru.tablet.assistant;
import android.app.Activity; import android.os.Bundle; import android.content.Intent; import android.provider.Settings; import android.widget.*; import android.graphics.Color; import android.graphics.Typeface;
public class MainActivity extends Activity {
 void label(LinearLayout l,String t,int size,String color) {TextView v=new TextView(this);v.setText(t);v.setTextSize(size);v.setTextColor(Color.parseColor(color));v.setPadding(0,12,0,20);l.addView(v);}
 public void onCreate(Bundle b) {super.onCreate(b); ScrollView scroll=new ScrollView(this); LinearLayout l=new LinearLayout(this);l.setOrientation(1);l.setPadding(32,40,32,40);scroll.addView(l);
 label(l,"ATELIER  /  TABLET ASSISTANT",12,"#C9AA70");label(l,"Ваш голос.\nВаше действие.",34,"#F3EEE5");
 label(l,"Прототип 0.1 • Русская диктовка",14,"#C9AA70");
 label(l,"01  Включите помощника",20,"#F3EEE5");
 label(l,"Разрешите сервис в специальных возможностях. После включения появится кнопка «Диктовать».",16,"#AEB3BA");
 Button setup=new Button(this);setup.setText("Открыть специальные возможности");setup.setTextColor(Color.parseColor("#101214"));setup.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#C9AA70")));setup.setOnClickListener(v->startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));l.addView(setup);
 label(l,"02  Выберите поле",20,"#F3EEE5");label(l,"Откройте заметки или другое приложение. Нажмите обычное текстовое поле, затем плавающую кнопку.",16,"#AEB3BA");
 label(l,"03  Продиктуйте и проверьте",20,"#F3EEE5");label(l,"Распознанный текст можно исправить. Нажатие «Вставить» заменяет содержимое выбранного поля целиком. Сообщения не отправляются.",16,"#AEB3BA");
 label(l,"Контроль остаётся у вас",20,"#F3EEE5");label(l,"Долгое нажатие на плавающую кнопку выключает сервис. Парольные поля исключены. Не все приложения поддерживают вставку. Голосовой сервис устройства может передавать аудио своему провайдеру.",14,"#AEB3BA");
 label(l,"Проверка: начните с пустой заметки.\nЭто диктовка, а не полноценный ИИ-агент.",14,"#C9AA70");setContentView(scroll);}
}