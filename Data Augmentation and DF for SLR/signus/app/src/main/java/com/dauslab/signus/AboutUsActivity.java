package com.dauslab.signus;

import android.content.Context;

import androidx.annotation.NonNull;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;

public class AboutUsActivity extends MaterialAboutActivity {

    @Override
    @NonNull
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context) {
        MaterialAboutTitleItem materialAboutTitleItem = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutAppTitle))
                .desc(getString(R.string.aboutAppDescription))
                .icon(R.mipmap.ic_launcher).build();

        MaterialAboutCard card = new MaterialAboutCard.Builder()
                .title(getString(R.string.aboutAppTitle))
                .addItem(materialAboutTitleItem)
                .build();

        MaterialAboutTitleItem materialAboutTitleItem2 = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutLogoTitle))
                .desc(getString(R.string.aboutLogoDescription))
                .icon(R.drawable.logo_us).build();

        MaterialAboutTitleItem materialAboutTitleItem3 = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutOwnersMacarena))
                .icon(R.drawable.macarena).build();
        MaterialAboutTitleItem materialAboutTitleItem4 = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutOwnersMarina))
                .icon(R.drawable.marina).build();
        MaterialAboutTitleItem materialAboutTitleItem5 = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutOwnersMAM))
                .icon(R.drawable.miguelangel).build();
        MaterialAboutTitleItem materialAboutTitleItem6 = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutOwnersFernando))
                .icon(R.drawable.fernando).build();
        MaterialAboutTitleItem materialAboutTitleItem7 = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutOwnersJuan))
                .icon(R.drawable.juan).build();
        MaterialAboutTitleItem materialAboutTitleItem8 = new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.aboutOwnersLuismi))
                .icon(R.drawable.luismi).build();

        MaterialAboutCard card2 = new MaterialAboutCard.Builder()
                .title(getString(R.string.aboutUsTitle))
                .addItem(materialAboutTitleItem2)
                .addItem(materialAboutTitleItem3)
                .addItem(materialAboutTitleItem4)
                .addItem(materialAboutTitleItem5)
                .addItem(materialAboutTitleItem6)
                .addItem(materialAboutTitleItem7)
                .addItem(materialAboutTitleItem8)
                .build();


        return new MaterialAboutList.Builder()
                .addCard(card)
                .addCard(card2)
                .build();
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.mal_title_about);
    }

}