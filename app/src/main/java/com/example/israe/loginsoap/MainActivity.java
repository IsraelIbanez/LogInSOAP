package com.example.israe.loginsoap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends Activity {
    EditText usr;
    EditText psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr = (EditText) findViewById(R.id.txtUsuario);
        psw = (EditText) findViewById(R.id.txtContrasenia);
    }


    public void ingresar(View v){
        if((!usr.getText().toString().equals("")) && (!psw.getText().toString().equals(""))){
            new conexionSOAP().execute(usr.getText().toString().trim(),psw.getText().toString().trim());

        }else{
            if((usr.getText().toString().equals(""))|| (psw.getText().toString().equals(""))){
                Toast toast = Toast.makeText(getApplicationContext(),"Llena los campos.",Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),"Datos erróneos.",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private class conexionSOAP extends AsyncTask<String, String, String>
    {
        static final String NAMESPACE="http://WS/";
        static final String METHODNAME ="validar";
        static final String URL ="http://192.168.0.13:8080/WBSOAP/ServicioSOAP?WSDL";
        static final String SOAP_ACTION =NAMESPACE+METHODNAME;


        @Override
        protected String doInBackground(String... params) {
            String res = "";
            SoapObject request = new SoapObject(NAMESPACE,METHODNAME);
            request.addProperty("usuario",params[0]);
            request.addProperty("contrasenia",params[1]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte= new HttpTransportSE(URL);
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                res = response.toString();
                Log.d("response",response.toString());
            }catch(Exception e){
                Log.d("Error",e.getMessage());
            }
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("response",s.toString());
            if(s.equals("s")){
                entrar(1);
            }else{
                entrar(2);
            }
        }
    }

    public void entrar(int num){
        if(num == 1) {
            Intent envia = new Intent(this, Principal.class);
            Bundle datos = new Bundle();
            datos.putString("usuario", usr.getText().toString());
            envia.putExtras(datos);
            finish();
            startActivity(envia);

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Datos erroneos",Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
