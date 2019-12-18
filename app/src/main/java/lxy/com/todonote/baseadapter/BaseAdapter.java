package lxy.com.todonote.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 *
 * @author 刘晓阳
 * @date 2018/1/17
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected List<T> list;
    private int layoutId;
    protected OnItemClickListener listener;
    protected OnItemLongClickListener longListener;
    protected OnItemChildClickListener childClickListener;


    public BaseAdapter( List<T> list,@LayoutRes int layoutId) {
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewHolder holder = ViewHolder.get(mContext,layoutId,parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.convertView.setOnClickListener(v -> {
            if (listener != null){
                listener.onClick(v,position);
            }
        });
        holder.convertView.setOnLongClickListener(v -> {
            if (longListener != null){
                longListener.onLongClick(v,position);
            }
            return true;
        });
        holder.setAdapter(this);
        convert(holder, list.get(position),position);

    }

    protected abstract void convert(ViewHolder holder, T t,int position);

    public T getItemByPosition(int position){
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 列表的每条 item 的点击事件
     */
    public interface OnItemClickListener{

        void onClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onLongClick(View view, int position);
    }

    public interface OnItemChildClickListener{
        void onCLick(BaseAdapter adapter,View view,int position);
    }

    public void setOnItemListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setOnItemLongListener(OnItemLongClickListener listener){
        this.longListener = listener;
    }

    public void addItemChildClickListener(OnItemChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }
}
