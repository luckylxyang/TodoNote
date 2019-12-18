package lxy.com.todonote.note;

import java.util.List;

import lxy.com.todonote.R;
import lxy.com.todonote.baseadapter.BaseAdapter;
import lxy.com.todonote.baseadapter.ViewHolder;

/**
 * Creator : lxy
 * date: 2019/12/14
 * @author lxy
 */
public class NoteAdapter extends BaseAdapter<NoteModel>{

    public NoteAdapter(List<NoteModel> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void convert(ViewHolder holder, NoteModel noteModel, int position) {
        holder.setText(R.id.tv_note_title,noteModel.getTitle())
                .setChecked(R.id.cb_note_finish,noteModel.getStatus() == 0 ? false : true);
        holder.addItemChildClickListener(R.id.note_delete);
        holder.addItemChildClickListener(R.id.cb_note_finish);
    }
}
