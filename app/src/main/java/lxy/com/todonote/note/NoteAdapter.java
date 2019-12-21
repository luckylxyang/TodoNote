package lxy.com.todonote.note;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

import java.util.List;

import lxy.com.todonote.R;
import lxy.com.todonote.baseadapter.BaseAdapter;
import lxy.com.todonote.baseadapter.ViewHolder;

/**
 * Creator : lxy
 * date: 2019/12/14
 *
 * @author lxy
 */
public class NoteAdapter extends BaseAdapter<NoteModel> {

    public NoteAdapter(List<NoteModel> list, int layoutId) {
        super(list, layoutId);
    }


    @Override
    protected void convert(ViewHolder holder, NoteModel noteModel, int position) {
        String title = noteModel.getTitle();
        SpannableStringBuilder ssb = new SpannableStringBuilder(title);
        if (noteModel.getStatus() == 1) {
            ssb.setSpan(new StrikethroughSpan(), 0, title.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.textGray)),
                    0,title.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        holder.setText(R.id.tv_note_title, ssb)
                .setText(R.id.tv_note_time,noteModel.getDateStr())
                .setChecked(R.id.cb_note_finish, noteModel.getStatus() == 0 ? false : true);
        holder.addItemChildClickListener(R.id.note_delete);
        holder.addItemChildClickListener(R.id.cb_note_finish);
    }
}
