package lxy.com.todonote.data;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import lxy.com.todonote.base.BaseRepository;
import lxy.com.todonote.net.NetworkManager;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.note.NoteModel;

/**
 * Creator : lxy
 * date: 2019/12/18
 */
public class LoginRepository extends BaseRepository {

    public MutableLiveData<Resource<List<NoteModel>>> getUndoList(int page,int status){
        MutableLiveData<Resource<List<NoteModel>>> liveData = new MutableLiveData<>();
        return observe(NetworkManager.getManager().getServer().getUndoList(page,status),liveData);
    }

}
