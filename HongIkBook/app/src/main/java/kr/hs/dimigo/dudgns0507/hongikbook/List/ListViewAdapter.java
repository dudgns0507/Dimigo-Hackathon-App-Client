package kr.hs.dimigo.dudgns0507.hongikbook.List;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dimigo.dudgns0507.hongikbook.R;

/**
 * Created by pyh42 on 2016-12-10.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context mContext = null;
    public ArrayList<ListData> mListData = new ArrayList<ListData>();

    public ListViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getId(int position) {
        return mListData.get(position).id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);

            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.author = (TextView)convertView.findViewById(R.id.author);
            holder.publisher = (TextView)convertView.findViewById(R.id.publisher);
            holder.state = (TextView)convertView.findViewById(R.id.state);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        ListData mData = mListData.get(position);

        holder.title.setText(mData.title);
        holder.author.setText("저자 : " + mData.author);
        holder.publisher.setText("출판사 : " + mData.publisher);
        if(!mData.state) {
            holder.state.setText("대여 가능");
            holder.state.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            holder.state.setText("대여 불가");
            holder.state.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        return convertView;
    }

    public void addItem(String title, String author, String publisher, boolean state, int id){
        ListData addInfo = null;
        addInfo = new ListData();

        addInfo.title = title;
        addInfo.author = author;
        addInfo.publisher = publisher;
        addInfo.state = state;
        addInfo.id = id;

        mListData.add(addInfo);
    }

    public void remove(int position){
        mListData.remove(position);
        dataChange();
    }

    public void dataChange(){
        this.notifyDataSetChanged();
    }
}
