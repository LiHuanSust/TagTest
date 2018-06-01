package com.example.tagtest.values;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tagtest.DataAdapter;
import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.drawer.User;
import com.example.tagtest.tools.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import android.support.v7.widget.Toolbar;

public class ActivitySearch extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<MyData> mAdapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private Set<MyData> listSelect;
    private List<MyData> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActivityCollector.addActivity(this);
        initialise();

    }
    public void initialise()
    {

       toolbar= findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // toolbar.setNavigationIcon(R.drawable.category);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        listView=findViewById(R.id.list_view_search);
        listView.setEmptyView(findViewById(R.id.layout_empty));
        list= DataSupport.where("user=?", User.getNowUserName()).find(MyData.class);
        listSelect=new HashSet<>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        //从此处获得searchView实例
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconified(false);
        searchView.setQueryHint("请输入搜索内容");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null && !newText.equals(""))
                {
                    Log.d("Test","coming");
                    for(MyData temp:list)
                    {
                        if(temp.getTypeSelect().contains(newText) ||
                                (temp.getMoney()+"").contains(newText))
                        {
                            listSelect.add(temp);
                        }
                    }
                    List<MyData> mList=new ArrayList<>();
                    mList.addAll(listSelect);
                    mAdapter=new DataAdapter(ActivitySearch.this,R.layout.list_view_basic_show,mList);
                    listView.setAdapter(mAdapter);
                    Log.d("searchACtivity","hello "+mList.size());
                    listSelect.clear();

                }
                else
                {

                }

                return  true;
            }

        });
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}


