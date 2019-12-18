package lxy.com.todonote.baseadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Spannable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashSet;


/**
 * Created by 刘晓阳 on 2018/2/1.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;
    public View convertView;
    private BaseAdapter mAdapter;
    private final LinkedHashSet<Integer> childClickViewIds;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.convertView = itemView;
        views = new SparseArray<>();
        childClickViewIds = new LinkedHashSet<>();
    }

    public static ViewHolder get(Context context, int layoutId, ViewGroup parentView){
        View view = LayoutInflater.from(context).inflate(layoutId,parentView,false);

        ViewHolder holder = new ViewHolder(context,view);
        return holder;
    }

    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null){
            view = convertView.findViewById(viewId);
            views.append(viewId,view);
        }

        return (T) view;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolder setText(int viewId, String message){
        TextView tv = getView(viewId);
        tv.setText(message);
        return this;
    }

    public ViewHolder setText(int viewId, Spannable message){
        TextView tv = getView(viewId);
        tv.setText(message);
        return this;
    }

    public ViewHolder setText(int viewId, CharSequence message){
        TextView tv = getView(viewId);
        tv.setText(message);
        return this;
    }

    @SuppressLint("ResourceType")
    public ViewHolder setImageResource(int viewId, @IdRes int imageId){
        ImageView iv = getView(viewId);
        iv.setImageResource(imageId);
        return this;
    }

    public ViewHolder setChecked(int viewId,boolean isCheck){
        View view = getView(viewId);
        if (view instanceof CompoundButton){
            ((CompoundButton) view).setChecked(isCheck);
        }
        return this;
    }

    public void addItemChildClickListener(int viewId){
        childClickViewIds.add(viewId);
        View view = getView(viewId);
        if (view != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapter.childClickListener != null){
                        mAdapter.childClickListener.onCLick(mAdapter,view,getAdapterPosition());
                    }
                }
            });
        }
    }

    public <T> void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
    }
}
