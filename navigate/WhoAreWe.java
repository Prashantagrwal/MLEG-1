package com.example.dell.navigate;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;


public class WhoAreWe extends AppCompatActivity
{
    Intent intent;int i;
    TextView tv;
    String about_us="MLEG- ( MARUTI LIVE EMOTIONS GALLERY) IS A DESIGNING & PRINTING FIRM, SITUATED IN RAIPUR, CHHATTISGARH.\n" +
            "\n" +
            "IT WAS FOUNDED BY NAVEEN GOYAL IN NOV 2015.\n" +
            "\n" +
            "THE FIRM DEALS IN VARIOUS PRODUCTS AS SUCH PRINTING AND DESIGNING OF ALL STATIONARY ITEM LIKE VISITING CARDS, BANNER/FLEX, BILL BOOK LETTERHEAD, BOOK ETC\n" +
            "\n" +
            "WITH 4 SEASON , A YEAR BECOMES\n" +
            "SAME AS WE WORK THROUGH OUT THE YEARS WITH MORE DESIGNING AND PRINTING JOBS IN ORDER TO BRING YOU AND YOUR CONFORT JUST IN ONE PLACE\n" +
            "\n" +
            "SEASONAL PRODUCTS LIKE GREETINGS, WEDDING ALBUMS, DIARIES , MAZINES, ETC. ARE ALSO BEING AVAILABLE.\n" +
            "\n" +
            "THE UNIQUENESS OF THE FIRM IS PERSONALISATION OF PRODUCTS.. \n" +
            "YES , WE DESIGN PRODUCTS AS IF ITS YOUR  EMOTIONS  PROVINDING WITH ATTRACTIVE PRESENTATION, LINE /SHYARI TO MAKE SOME ONE SPECIAL A MORE SPECIAL.\n" +
            "[26/03 13:10] Naveen: HERE COMES TO DESIGNER SECTION AS WE MANUFACTURE WEDDING CARDS WITH OUR OWN CONCEPTS AND DESIGNS. SPECIALLY UPON THIS WE TAKE CUSTOMERS VIEW AND PROVIDE CARDS ACCORDING TO THEIR CHOICE  OF DESIGNS , LINES , EMOTIONS. \n" +
            "\n" +
            "ATTRACTION FALLS FOR THE SMARTER ONE FIRST? IS THAT TRUE?\n" +
            "SO SMART JOB IS HERE WITH SHAYARI , IDEAS, MATTER, THEME,  FOR EVENTS, PARTIES, WEDDINGS, SPECIAL MOMENTS, ETC\n" +
            "\n" +
            "HOPE TO SEE YOU ALL\n" +
            "YOUR SMILE IS WAITING WITH US.";

    String contact_us="Maruti Live Emotons Gallery \n" +
            "Opp. Maruti Business Park, Beside City Plaza Complex\n" +
            "Bhilai Road, Raipur (C.G.) 492001\n" +
            "\n" +
            "93000-12490, 90398-12490\n" +
            "naveengl83@gmail.com\n" +
            "marutiliveemotionsgallery@gmail.com\n" +
            "\n";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name_title) + "</font>"));
        intent=getIntent();
        tv= (TextView) findViewById(R.id.tv_about_us);
        if(intent!=null) i=intent.getIntExtra("check",0);
        ids(i);
    }

    private void ids(int i)
    {
     if(i==1)
     {
        tv.setText(about_us);
     }
        else if(i==2)
     {
          tv.setText(contact_us);
     }
    }
}
