package jp.dip.th075altlobby.imo.Data.communication;

public class ClientInfo {
    private final AbstractInputThread in;
    private final AbstractOutputThread out;
    private UserInfo info;

    /**
     * 入力用スレッドと出力用スレッドを指定してインスタンスを構築する.
     * 
     * @param in
     *            入力用スレッド
     * @param out
     *            出力用スレッド
     */
    public ClientInfo(AbstractInputThread in, AbstractOutputThread out) {
        this.in = in;
        this.out = out;
    }

    /**
     * このインスタンスが保持している入力用スレッドを返す.
     * 
     * @return 入力用スレッド
     */
    public AbstractInputThread getInputThread() {
        return this.in;
    }

    /**
     * このインスタンスが保持している出力用スレッドを返す.
     * 
     * @return 出力用スレッド
     */
    public AbstractOutputThread getOutputThread() {
        return this.out;
    }

    /**
     * <h1>setUserInfo</h1> <h2>このインスタンスが保持しているUserInfoを設定する</h2>
     * <p>
     * UserInfoには、「公開名」、「UID」、「IPアドレス」、「IM公開ポリシー」、「公開メッセージ」、「公開ショートメッセージ」が含まれます.
     * 詳細は、UserInfoを参照してください。
     * </p>
     * <p>
     * このメソッドはUserInfoの同期をとります。
     * </p>
     * 
     * @param info
     *            設定するユーザー情報が格納されたUserInfo
     * @see UserInfo
     */
    public synchronized void setUserInfo(UserInfo info) {
        this.info = info;
    }

    /**
     * <h1>getUserInfo</h1> <h2>このインスタンスが保持しているUserInfoを取得する</h2>
     * <p>
     * UserInfoには、「公開名」、「UID」、「IPアドレス」、「IM公開ポリシー」、「公開メッセージ」、「公開ショートメッセージ」が含まれます.
     * 詳細は、UserInfoを参照してください。
     * </p>
     * <p>
     * このメソッドはUserInfoの同期をとります。
     * </p>
     * 
     * @return ユーザー情報が格納されたUserInfo
     * @see UserInfo
     */
    public synchronized UserInfo getUserInfo() {
        return this.info;
    }
}
