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

    public MutableLiveData<Resource<List<NoteModel>>> getUndoList(int page) {
        Log.i("sfdf","1234");
        return repository.getUndoList(page);
    }

    public MutableLiveData<Resource<List<NoteModel>>> refresh(){
        return getUndoList(0);
    }

    public void deleteNote(int id){
        NetworkManager.getManager().getServer().deleteNoteById(id)
                .compose(RxHelper.observableIO2Main())
                .subscribe(new BaseObserver<String>(){
                    @Override
                    public void onSuccess(String s) {
                        ToastUtils.show("删除成功");
                    }

                    @Override
                    public void onFailure(String message) {
                        ToastUtils.show(message);
                    }
                });
    }

    public void updateNoteStatus(int id,int status){
        NetworkManager.getManager().getServer().updateNoteStatus(id, status)
                .compose(RxHelper.observableIO2Main())
                .subscribe(new BaseObserver<NoteModel>(){
                    @Override
                    public void onSuccess(NoteModel s) {

                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
    }

}
