package foo.bar.HelloWorld;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.util.ArraySet;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.Button;

import java.util.List;
import java.util.Set;


import android.app.Activity;  
import android.os.ServiceManager;  
import android.os.Bundle;  
import android.os.IHelloService;  
import android.os.RemoteException;  
import android.util.Log;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.EditText; 
import android.os.IBinder;
import com.android.internal.os.BinderInternal;

public class Hello extends Activity implements OnClickListener {  
    private final static String LOG_TAG = "shy.luo.renju.Hello";  
      
    private IHelloService helloService = null;  
  
    private EditText valueText = null;  
    private Button readButton = null;  
    private Button writeButton = null;  
    private Button clearButton = null;  
	private IBinder helloIBinder;      
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
  		helloIBinder=ServiceManager.getService("hello");
		if(helloIBinder != null)
    		helloService = IHelloService.Stub.asInterface(helloIBinder);  
        else
			Log.i(LOG_TAG, "liusk:get hello service fail");  
        valueText = (EditText)findViewById(R.id.edit_value);  
        readButton = (Button)findViewById(R.id.button_read);  
        writeButton = (Button)findViewById(R.id.button_write);  
        clearButton = (Button)findViewById(R.id.button_clear);  
  
    readButton.setOnClickListener(this);  
    writeButton.setOnClickListener(this);  
    clearButton.setOnClickListener(this);  
          
        Log.i(LOG_TAG, "Hello Activity Created");  
    }  
      
    @Override  
    public void onClick(View v) {  
        if(v.equals(readButton)) {  
        try {  
                int val = helloService.getVal();  
                String text = String.valueOf(val);  
                valueText.setText(text);  
        } catch (RemoteException e) {  
            Log.e(LOG_TAG, "Remote Exception while reading value from device.");  
        }         
        }  
        else if(v.equals(writeButton)) {  
        try {  
                String text = valueText.getText().toString();  
                int val = Integer.parseInt(text);  
            helloService.setVal(val);  
        } catch (RemoteException e) {  
            Log.e(LOG_TAG, "Remote Exception while writing value to device.");  
        }  
        }  
        else if(v.equals(clearButton)) {  
            String text = "";  
            valueText.setText(text);  
        }  
    }  
}  
