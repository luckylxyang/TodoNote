package lxy.com.todonote.note;

/**
 * Creator : lxy
 * date: 2019/12/14
 */
public class NoteModel {


    /**
     * completeDate : null
     * completeDateStr :
     * content : 开发一个微信小程序，云开发，云记账。
     * date : 1569772800000
     * dateStr : 2019-09-30
     * id : 16601
     * priority : 0
     * status : 0  //是否完成 ，0 未完成，1 完成
     * title : 记账小程序
     * type : 2
     * userId : 3825
     */

    private Object completeDate;
    private String completeDateStr;
    private String content;
    private long date;
    private String dateStr;
    private int id;
    private int priority;
    private int status;
    private String title;
    private int type;
    private int userId;

    public Object getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Object completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDateStr() {
        return completeDateStr;
    }

    public void setCompleteDateStr(String completeDateStr) {
        this.completeDateStr = completeDateStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
