package lxy.com.todonote.net;


import io.reactivex.Observable;
import lxy.com.todonote.base.BasePageModel;
import lxy.com.todonote.login.LoginModel;
import lxy.com.todonote.note.NoteModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author : lxy
 * date: 2019/1/15
 */

public interface NetworkAPI {


    // 登录注册

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginModel>> login(@Field("username") String username,
                                               @Field("password") String password);

    /**
     * http://www.wanandroid.com/user/logout/json
     *
     * @return
     */
    @GET("user/logout/json")
    Observable<LoginModel> logout();

    /**
     * http://www.wanandroid.com/user/register
     *
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginModel>> register(@Field("username") String username,
                                                  @Field("password") String password,
                                                  @Field("repassword") String repassword);

    /**
     * 分页获取所有的代办列表
     *
     * @param page
     * @return
     */
    @GET("lg/todo/v2/list/{page}/json")
    Observable<BaseResponse<BasePageModel<NoteModel>>> getUndoList(@Path("page") int page);


    /**
     * 删除一个代办
     *
     * @param id
     * @return
     */
    @POST("lg/todo/delete/{id}/json")
    Observable<BaseResponse<String>> deleteNoteById(@Path("id") int id);

    /**
     * 仅更新note的状态 未完成 -> 完成 ， 完成 -> 未完成
     *
     * @param id
     * @param status
     * @return
     */
    @POST("lg/todo/done/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<NoteModel>> updateNoteStatus(@Path("id") int id, @Field("status") int status);

    /**
     * 更新 note 内容
     *
     * @param id
     * @param title
     * @param content
     * @param dateStr
     * @param status
     * @return
     */
    @POST("lg/todo/update/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<NoteModel>> updateNote(@Path("id") int id,
                                                   @Field("title") String title,
                                                   @Field("content") String content,
                                                   @Field("date") String dateStr,
                                                   @Field("status") int status);

    @POST("lg/todo/add/json")
    @FormUrlEncoded
    Observable<BaseResponse<NoteModel>> addNote(@Field("title") String title,
                                                @Field("content") String content,
                                                @Field("date") String dateStr,
                                                @Field("type") int type,
                                                @Field("priority")int priority);
}
