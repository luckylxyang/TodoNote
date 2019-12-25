package lxy.com.todonote.note;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import lxy.com.todonote.base.BasePageModel;
import lxy.com.todonote.base.BaseViewModel;
import lxy.com.todonote.data.NoteRepository;
import lxy.com.todonote.net.BaseObserver;
import lxy.com.todonote.net.NetworkManager;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.net.RxHelper;
import lxy.com.todonote.utils.ToastUtils;

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
    public int page = 1;
    public int doPage = 1;
    public List<NoteModel> models;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository();
        hasUndo = true;
        models = new ArrayList<>();
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getUndoList(int page, int status) {
        return repository.getUndoList(page, status);
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> refresh() {
        page = 1;
        doPage = 1;
        hasUndo = true;
        return getAllTodoList();
    }

    public MutableLiveData<Resource<String>> deleteNote(int id) {
        return repository.deleteUntoNote(id);
    }

    public MutableLiveData<Resource<NoteModel>> updateNoteStatus(int id, int status) {
        return repository.updateNoteStatus(id, status);
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getAllTodoList() {
        if (hasUndo) {
            return getUndoList(page, 0);
        }else {
            return getUndoList(doPage, 1);
        }
    }

}
