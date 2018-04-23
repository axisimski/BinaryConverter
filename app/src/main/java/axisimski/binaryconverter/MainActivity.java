package axisimski.binaryconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

   static EditText input;
   static TextView output;
   static RadioButton twosComplement;
   static RadioButton signedNum;

   static String makeSelection, mustBeBinary, invalidNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input=(EditText) findViewById(R.id.input);
        output=(TextView)findViewById(R.id.output);
        twosComplement=(RadioButton)findViewById(R.id.twosComplement);
        signedNum=(RadioButton)findViewById(R.id.signeNum);

        makeSelection = getResources().getString(R.string.makeSelection);
        mustBeBinary = getResources().getString(R.string.mustBeBinary);
        invalidNumber = getResources().getString(R.string.notvalid);


    }

    //Perform conversion
    public void toBinary (View v){

        if(input.getText().toString().isEmpty()) {

            return;
        }
        else{

            if(signedNum.isChecked()){

                ToBinary bin=new ToBinary();
                String binString = bin.convert(Double.parseDouble(input.getText().toString()));
                binString=bin.convert(binString);
                //===============================================================================================
                Double x=Double.parseDouble(input.getText().toString());
                ToHex tohex=new ToHex();
                String hexString= tohex.convert(x.toString());
                binString="Dec: "+input.getText().toString()+"\n\nBin: "+binString+"\n\nHex: "+hexString;

                MainActivity.output.setText(binString);
            }

            else if(twosComplement.isChecked()){

                //Limited input due to app crashing
                if(Double.parseDouble(input.getText().toString())>99999999.999||
                        Double.parseDouble(input.getText().toString())<-99999999.999){

                    Toast.makeText(this,"-100,000,000< N < 100,000,000",Toast.LENGTH_SHORT).show();
                 }


                else  {

                    ToBinary bin = new ToBinary();
                    String bs = bin.convert(Double.parseDouble(input.getText().toString()));
                    String ns = bin.toTwosComplement(bs);

                    Double x=Double.parseDouble(input.getText().toString());
                    ToHex tohex=new ToHex();
                    String hexString= tohex.convert(x.toString());
                    ns="Dec: "+input.getText().toString()+"\n\nBin: "+ns+"\n\nHex: "+hexString;

                    MainActivity.output.setText(ns);
                }
            }

            else{

                Toast.makeText(this,makeSelection,Toast.LENGTH_SHORT).show();            }

        }

    }
    //====================================================================end of toBinary

    public void toDecimal (View v){

        if(!input.getText().toString().isEmpty()){

            if(!input.getText().toString().matches("[0.1-]+")){

                Toast.makeText(this,mustBeBinary,Toast.LENGTH_SHORT).show();

            }

            else{

                if(signedNum.isChecked()){
                  ToDecimal dec=new ToDecimal();
                  Double decimalString= dec.ConvertToDecimal(input.getText().toString());
                  String dectoString=decimalString.toString();

                  Double x=Double.parseDouble(dectoString);
                  ToHex tohex=new ToHex();
                  String hexString= tohex.convert(x.toString());
                  dectoString="Dec: "+dectoString.toString()+"\n\nBin: "+input.getText().toString()+"\n\nHex: "+hexString;

                  MainActivity.output.setText(dectoString);
                }

                else if(twosComplement.isChecked()){

                    if(input.getText().toString().contains("-")){
                        Toast.makeText(this,invalidNumber,Toast.LENGTH_SHORT).show();
                    }

                    else{

                         ToDecimal dec = new ToDecimal();
                         String ns = dec.twosComplementToDec(input.getText().toString());
                         Double decimalDouble= dec.ConvertToDecimal(ns);
                         String ds=Double.toString(decimalDouble);

                        Double x=Double.parseDouble(ds);
                        ToHex tohex=new ToHex();
                        String hexString= tohex.convert(x.toString());
                        ds="Dec: "+ds.toString()+"\n\nBin: "+input.getText().toString()+"\n\nHex: "+hexString;

                         MainActivity.output.setText(ds);
                    }
                }

                else{
                    Toast.makeText(this,makeSelection,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //====================================================================end of toDecimal


    public void fromHex(View v){

        if(input.getText().toString().isEmpty()) {

            return;
        }
        else{

            if(signedNum.isChecked()){

                FromHex FH= new FromHex();

                String bin=FH.convert(input.getText().toString());

    //!!!            //MUST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DO ERROR CHECKING MAX 8!!!!!!!!!!!!!!

                ToDecimal bin2dec=new ToDecimal();
                Double dec=bin2dec.ConvertToDecimal(bin);
                //===============================================================================================

                String  outString="Dec: "+dec.toString()+"\n\nBin: "+bin+"\n\nHex: "+input.getText().toString();

                MainActivity.output.setText(outString);
            }

            //hex to dec and signed bin



            else if(twosComplement.isChecked()){

                //Limited input due to app crashing
                if(Double.parseDouble(input.getText().toString())>99999999.999||
                        Double.parseDouble(input.getText().toString())<-99999999.999){

                    Toast.makeText(this,"-100,000,000< N < 100,000,000",Toast.LENGTH_SHORT).show();
                }


                else  {

                    FromHex FH= new FromHex();
                    ToBinary TB=new ToBinary();

                    String bin=FH.convert(input.getText().toString());

                    ToDecimal bin2dec=new ToDecimal();
                    Double dec=bin2dec.ConvertToDecimal(bin);
                    String TC=TB.toTwosComplement(bin);
                    //===============================================================================================

                    String  outString="Dec: "+dec.toString()+"\n\nBin: "+TC+"\n\nHex: "+input.getText().toString();

                    MainActivity.output.setText(outString);
                }
            }

            else{

                Toast.makeText(this,makeSelection,Toast.LENGTH_SHORT).show();            }

        }



    }//end of from hex

}


