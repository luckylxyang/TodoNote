package lxy.com.todonote.data;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Observable;
import lxy.com.todonote.base.BaseRepository;
import lxy.com.todonote.login.LoginModel;
import lxy.com.todonote.net.BaseResponse;
import lxy.com.todonote.net.NetworkManager;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.net.RxHelper;
import lxy.com.todonote.note.NoteModel;

/**
 * Creator : lxy
 * date: 2019/12/18
 */
public class NoteRepository extends BaseRepository{

    public MutableLiveData<Resource<List<NoteModel>>> getUndoList(int page){
        MutableLiveData<Resource<List<NoteModel>>> liveData = new MutableLiveData<>();
        return observe(NetworkManager.getManager().getServer().getUndolist(page),liveData);
    }


}
