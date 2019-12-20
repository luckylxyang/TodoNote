package lxy.com.todonote.data;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import lxy.com.todonote.base.BasePageModel;
import lxy.com.todonote.base.BaseRepository;
import lxy.com.todonote.net.NetworkManager;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.note.NoteModel;

/**
 * Creator : lxy
 * date: 2019/12/18
 */
public class NoteRepository extends BaseRepository{

    public MutableLiveData<Resource<BasePageModel<NoteModel>>> getUndoList(int page){
        MutableLiveData<Resource<BasePageModel<NoteModel>>> liveData = new MutableLiveData<>();
        return observe(NetworkManager.getManager().getServer().getUndoList(page),liveData);
    }

    public MutableLiveData<Resource<String>> deleteUntoNote(int id){
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observe(NetworkManager.getManager().getServer().deleteNoteById(id),liveData);
    }

    public MutableLiveData<Resource<NoteModel>> updateNoteStatus(int id,int status){
        MutableLiveData<Resource<NoteModel>> liveData = new MutableLiveData<>();
        return observe(getServer().updateNoteStatus(id, status),liveData);
    }


}
