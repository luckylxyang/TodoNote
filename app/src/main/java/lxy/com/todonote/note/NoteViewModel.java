package lxy.com.todonote.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import lxy.com.todonote.base.BasePageModel;
import lxy.com.todonote.base.BaseViewModel;
import lxy.com.todonote.data.NoteRepository;
import lxy.com.todonote.net.Resource;

/**
 * Creator : lxy
 * date: 2019/12/14
 */
public class NoteViewModel extends BaseViewModel {

    public NoteRepository repository;
    /**
     * 是否还有未完成列表
     */
    public boolean hasUndo;
    public int page = 2;
    public List<NoteModel> models;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository();
        hasUndo = true;
        models = new ArrayList<>();
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getUndoList() {
        return repository.getUndoList(page, 1);
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> refresh() {
        page = 2;
        return getFirstTodoList();
    }

    public MutableLiveData<Resource<String>> deleteNote(int id) {
        return repository.deleteUntoNote(id);
    }

    public MutableLiveData<Resource<NoteModel>> updateNoteStatus(int id, int status) {
        return repository.updateNoteStatus(id, status);
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getFirstTodoList() {
        return repository.getFirstList();
    }

}
