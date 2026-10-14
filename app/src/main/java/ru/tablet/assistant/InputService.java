package ru.tablet.assistant;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.*;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.os.Bundle;
public class InputService extends AccessibilityService {
 static InputService instance;
 private AccessibilityNodeInfo target;
 private WindowManager wm;
 private Button button;
 protected void onServiceConnected() {
  instance=this; wm=(WindowManager)getSystemService(WINDOW_SERVICE);
  button=new Button(this); button.setText("Диктовать"); button.setTextColor(android.graphics.Color.parseColor("#101214")); button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#C9AA70")));
  WindowManager.LayoutParams p=new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,android.graphics.PixelFormat.TRANSLUCENT);
  p.gravity=Gravity.TOP|Gravity.END; p.y=180;
  wm.addView(button,p);
  button.setOnClickListener(v-> {
   if (target==null || !target.refresh() || !target.isEditable() || target.isPassword()) { toast("Сначала нажмите обычное текстовое поле"); return; }
   Intent i=new Intent(this,DictationActivity.class); i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(i);
  });
  button.setOnLongClickListener(v->{disableSelf(); return true;});
 }
 public void onAccessibilityEvent(AccessibilityEvent e) {
  if (getPackageName().contentEquals(e.getPackageName()==null ? "" : e.getPackageName())) return;
  AccessibilityNodeInfo n=e.getSource();
  if(n!=null && n.isEditable() && n.isFocused() && !n.isPassword()) {
   if(target!=null) target.recycle(); target=AccessibilityNodeInfo.obtain(n);
  }
  if(n!=null) n.recycle();
 }
 boolean insert(String text) {
  if(target==null || !target.refresh() || !target.isEditable() || target.isPassword()) return false;
  Bundle args=new Bundle(); args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,text);
  boolean accepted=target.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,args);
  return accepted && target.refresh() && text.contentEquals(target.getText()==null ? "" : target.getText());
 }
 void toast(String text) {Toast.makeText(this,text,Toast.LENGTH_LONG).show();}
 public void onInterrupt() {}
 public void onDestroy() { if(button!=null && wm!=null) wm.removeView(button); if(target!=null) target.recycle(); instance=null; super.onDestroy(); }
}