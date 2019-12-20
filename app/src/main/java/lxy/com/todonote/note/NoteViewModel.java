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

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository();
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getUndoList(int page) {
        return repository.getUndoList(page);
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> refresh(){
        return getUndoList(0);
    }

    public MutableLiveData<Resource<String>> deleteNote(int id){
        return repository.deleteUntoNote(id);
    }

    public MutableLiveData<Resource<NoteModel>> updateNoteStatus(int id,int status){
        return repository.updateNoteStatus(id, status);
    }

}
