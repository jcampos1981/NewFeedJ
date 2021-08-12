package com.crdesigns.newfeedj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    boolean existeDB = false;
    final MySQLiteHelper db = new MySQLiteHelper(this);
    TabLayout tabLayout;
    TabItem mhome;
    PageAdapter pageAdapter;
    Toolbar mtoolbar;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        mhome =findViewById(R.id.home);

        viewPager=findViewById(R.id.fragmentcontainer);
        tabLayout=findViewById(R.id.include);

        pageAdapter= new PageAdapter(getSupportFragmentManager(),6);
        viewPager.setAdapter(pageAdapter);

        DBWork();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0||tab.getPosition()==1){
                    pageAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
    private void DBWork(){
        existeDB = db.doesDatabaseExist(getApplicationContext(), "NewsDB");
        if (!existeDB) {
            db.addFavorites(new FavoritesClass("","","","","",""));
            db.addVideoUsers(new VideoUsers(
                    "https://player.vimeo.com/external/163914755.sd.mp4?s=b98042d47eca319e213e11087408bf73008c4178&profile_id=164",
                    "https://edge.stg-alefedge.com/v1/content?url=00c104e612cb7f461e6446d77125a0edb3b8a827df1bd1149f3a08cad05d4af7803edd46efcee7da7d3f9c34799b0043d0d0f934752949b6978b2409e57a890b"
                    ,"0")); //cat
            db.addVideoUsers(new VideoUsers(
                    "https://player.vimeo.com/external/330970621.sd.mp4?s=1b02e92dde89b0f65cc63e24cbd4837d66e16f3e&profile_id=165",
                    "https://edge.stg-alefedge.com/v1/content?url=00c104e612cb7f461e6446d77125a0edb3b8a827df1bd1149f3a08cad05d4af7ce28087a359bcdf9fa9f9535c26ca26916beecbeff01196ae60d59de27d41553",
                    "0")); //cat
            db.addVideoUsers(new VideoUsers(
                    "https://player.vimeo.com/external/315137091.sd.mp4?s=6a21c942a4aa09eaedf5d577ade566881a56d69e&profile_id=165",
                    "https://edge.stg-alefedge.com/v1/content?url=00c104e612cb7f461e6446d77125a0edb3b8a827df1bd1149f3a08cad05d4af7e957e1996e3304ba27ae79831d85fc727f9116e217cc00baf5676928c13975445748a5b3558caf50b652d9b89bedb145",
                    "0")); //city
            db.addVideoUsers(new VideoUsers("https://player.vimeo.com/external/161442861.sd.mp4?s=4b57703c2094b22ccfdf6a848d603cd8a7c208ba&profile_id=164",
                    "https://edge.stg-alefedge.com/v1/content?url=00c104e612cb7f461e6446d77125a0edb3b8a827df1bd1149f3a08cad05d4af7b15f3210a5257eccad8da3e24c2ed8eee1011d348cb7c1cd5e877d40d3fc8e10",
                    "0")); //music

        }
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
                searchView.clearFocus();
                item.collapseActionView();

                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tabLayout.getTabAt(0);
                tab.select();

                //Sending preferences to Homefragment
                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("searchval", query);
                editor.putString("channelname", "");
                editor.commit();

                //pageAdapter.getItem(0);
                //pageAdapter.notifyDataSetChanged();

                //pageAdapter.notifyDataSetChanged();
                //viewPager.setCurrentItem(tab.getPosition());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}