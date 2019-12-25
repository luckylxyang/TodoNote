package lxy.com.todonote.data;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import lxy.com.todonote.base.BasePageModel;
import lxy.com.todonote.base.BaseRepository;
import lxy.com.todonote.net.NetConstants;
import lxy.com.todonote.net.BaseResponse;
import lxy.com.todonote.net.NetworkManager;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.note.NoteModel;

/**
 * Creator : lxy
 * date: 2019/12/18
 */
public class NoteRepository extends BaseRepository{

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getUndoList(int page,int status){
        MutableLiveData<Resource<BasePageModel<NoteModel>>> liveData = new MutableLiveData<>();
        return observe(NetworkManager.getManager().getServer().getUndoList(page,status),liveData);
    }

    public MutableLiveData<Resource<String>> deleteUntoNote(int id){
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observe(NetworkManager.getManager().getServer().deleteNoteById(id),liveData);
    }

    public MutableLiveData<Resource<NoteModel>> updateNoteStatus(int id,int status){
        MutableLiveData<Resource<NoteModel>> liveData = new MutableLiveData<>();
        return observe(getServer().updateNoteStatus(id, status),liveData);
    }

    public MutableLiveData<Resource<NoteModel>> addNote(String title,String content,
                                                        String dateStr,int type,int priority){
        MutableLiveData<Resource<NoteModel>> liveData = new MutableLiveData<>();
        return observe(getServer().addNote(title, content, dateStr, type, priority),liveData);
    }

    public MutableLiveData<Resource<NoteModel>> updateNote(int id,String title,String content,
                                                        String dateStr,int type){
        MutableLiveData<Resource<NoteModel>> liveData = new MutableLiveData<>();
        return observe(getServer().updateNote(id,title, content, dateStr, type),liveData);
    }

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getAllList(int page,int status){
        Observable<BaseResponse<BasePageModel<NoteModel>>> undoList = NetworkManager.getManager().getServer().getUndoList(page, 1);
        Observable<BaseResponse<BasePageModel<NoteModel>>> doList = NetworkManager.getManager().getServer().getUndoList(page, 0);
        MutableLiveData<Resource<BasePageModel<NoteModel>>> liveData = new MutableLiveData<>();
        Observable<BaseResponse<BasePageModel<NoteModel>>> zip = Observable.zip(undoList, doList, (base1, base2) -> {
            if (base1.getErrorCode() == NetConstants.NET_SUCCESS && base1.getErrorCode() == NetConstants.NET_SUCCESS) {
//                base1.getData().getDatas()
            }
            return base1;
        });
        return observe(zip,liveData);
    }

}
