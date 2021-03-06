package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

public class AttractionsActivity extends AppCompatActivity
        implements  AttractionListFragment.OnFragmentInteractionListener,
                    AttractionMapFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private City city;
    private String cityJson;
    private boolean isFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        Bundle bundle = getIntent().getExtras();
        cityJson = bundle.getString("cityJson");
        Gson gson = new Gson();
        city = gson.fromJson(cityJson, City.class);
        isFavourites = bundle.getBoolean("isFavourites", false);
        if (!isFavourites){
            setTitle(city.getName() + " - " + getResources().getString(R.string.attractions));
        }
        else {
            setTitle(getResources().getString(R.string.favourites));
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        AttractionListFragment attractionListFragment = new AttractionListFragment();
        AttractionMapFragment attractionMapFragment = new AttractionMapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cityJson", cityJson);
        bundle.putBoolean("isFavourites", this.isFavourites);
        attractionListFragment.setArguments(bundle);
        attractionMapFragment.setArguments(bundle);


        adapter.addFragment(attractionListFragment, getResources().getString(R.string.LIST));
        adapter.addFragment(attractionMapFragment, getResources().getString(R.string.MAP));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
