package jp.dip.th075altlobby.imo.Data.communication;

/**
 * <h1>StateModifiedInfo</h1> <h2>ユーザー情報の更新通知をラップするクラス</h2>
 * <p>
 * DataSplitter.toStateModifiedInfoメソッドで利用されるユーザー情報の更新通知をラップするクラスです。
 * </p>
 * <p>
 * このクラスはユーザーを一意に特定できるString UIDとユーザーの新しい状態が格納されているbyte stateの二つのフィールドを持ちます。
 * </p>
 * <p>
 * このクラスのインスタンスは不可変です。インスタンスはスレッドに対して安全です。
 * </p>
 * 
 * @author Cyrill
 */
public class StateModifiedInfo {
    private final String UID;
    private final byte state;

    /**
     * <h1>StateModifiedInfo</h1> <h2>すべてのフィールドを指定してインスタンスを生成する。</h2>
     * <p>
     * すべてのフィールドを指定してインスタンスを生成します。
     * </p>
     * <p>
     * このクラスのインスタンスは不可変です。インスタンスはスレッドに対して安全です。
     * </p>
     * 
     * @param UID
     *            ユーザーを一意に特定できる文字列
     * @param state
     *            ユーザーの状態を示すbyte
     */
    public StateModifiedInfo(String UID, byte state) {
        this.UID = UID;
        this.state = state;
    }

    /**
     * <h1>getUID</h1> <h2>このインスタンスが保持しているUIDを取得する</h2>
     * <p>
     * このインスタンスが保持しているユーザーを一意に特定できるUIDを取得します。 UIDは不可変です。
     * </p>
     * 
     * @return このインスタンスが保持しているUID
     */
    public String getUID() {
        return UID;
    }

    /**
     * <h1>getState</h1> <h2>このインスタンスが保持しているユーザーの状態を取得する</h2>
     * <p>
     * このインスタンスが保持しているユーザーの状態を示す単一のbyteを取得します。 stateは不可変です。
     * </p>
     * 
     * @return このインスタンスが保持しているユーザーのステート
     */
    public byte getState() {
        return state;
    }

    /**
     * <h1>toString</h1> <h2>オブジェクトの文字列表現を返します。</h2>
     * <p>
     * このオブジェクトを「テキストで表現する」文字列を返します。
     * このメソッドはこのインスタンスが保持しているUIDとstateを読みやすい文字列として返します。
     * </p>
     * <p>
     * このメソッドは次の値と等しい文字列を返します。
     * </p>
     * <code><strong>"{ [UID/state] = ["+getUID()+"/"+getState()+"] }"</strong></code>
     * 
     * @return このオブジェクトの文字列表現
     */
    @Override
    public String toString() {
        return "{ [UID/state] = [" + getUID() + "/" + getState() + "] }";
    }
}
