package com.mondkars.mondkarsproduct;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.mondkars.mondkarsproduct.R;

public class AboutUsFragment  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_fragment);


        WebView view = (WebView) findViewById(R.id.textContent);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "उदरभरण नोहे... जाणी जो चवीचे मर्म' ही मोंडकर्स फूडची टॅगलाईन आहे. अलीकडच्या काळात बाहेर जाऊन खाणे सुरक्षित वाटेनासे झाले आहे. अशावेळी प्रारंभीच्या काळात केवळ मसाले आणि खाद्यपदार्थ उत्पादने ही खासियत राहिलेल्या मोंडकर्स फूडने अन्नपदार्थांची होम डिलिव्हरी सुरू केली. अल्प काळात सावंतवाडीकर ग्राहकांचा मोठा प्रतिसाद मिळाला. आता नियमितपणे ही सेवा दिली जात आहे. घरचे आणि दर्जेदार अन्नपदार्थ हे मोंडकर्स फूडचे वैशिष्ट्य राहिले आणि भविष्यात राहणार आहे. मालवणी फूडची खासियत खवय्यांना नक्कीच मिळणार आहे. फक्त मालवणी फूडच नव्हे, तर विविध प्रकारचे रूचकर  अन्नपदार्थही चाखता येणार आहेत.  मोंडकर्स फूडचे मुख्य उद्दिष्ट हे आरोग्यदायी,स्वच्छ आणि रूचकर पदार्थ पुरविणे हेच राहिले आहे आणि राहणार आहे. गेली अनेक वर्षे सावंतवाडीकरांनी भरभरून प्रेम दिलंय. सावंतवाडीकरांसोबतचं नातं हे  ग्राहक व विक्रेत्याचं नसून जणू एक कुटुंबच बनलंय. आजही वैविध्यपूर्ण खाद्यपदार्थ आम्ही घेऊन आलो आहोत, जे सावंतवाडीकरांना नक्कीच आवडतील !";
        text+= "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");
    }
}
