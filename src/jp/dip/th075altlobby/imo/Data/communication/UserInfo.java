package jp.dip.th075altlobby.imo.Data.communication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ユーザー情報が格納されるクラス。 このクラスの変更可能なフィールドはstateのみです。
 * 
 * @author Cyrill
 */
public class UserInfo {
    private final String publicName;
    private final String UID;
    private final byte[] ipAddress;
    private final Short casterPort;
    private AtomicInteger state;
    private final Byte IMPolicy;
    private final Byte IPHideFlag;
    private final String publicMessage;
    private final String publicShortMessage;

    public static final byte WAITING = 0, GATHERING = 1, SETTING = 2,
            FIGHTING = 3, WATCHING = 4, LEAVING = 5, HOST_GATHERING = 6,
            HOST_GATHERING_WAIT = 64;
    // HOST_GATHERING_WAITはクライアント内部で処理される特殊な状態

    public static final byte ACCEPT = 0, REFUSE_IF_FIGHTING = 1, REFUSE = 2;

    private static final Map<String, String> STATE_STR = new HashMap<String, String>();
    private static final Map<String, String> IM_STR = new HashMap<String, String>();

    static {
        STATE_STR.put("0", "待機中");
        STATE_STR.put("1", "募集中");
        STATE_STR.put("2", "設定中");
        STATE_STR.put("3", "対戦中");
        STATE_STR.put("4", "観戦中");
        STATE_STR.put("5", "退席中");
        STATE_STR.put("6", "ホスト募集中");

        STATE_STR.put("待機中", "0");
        STATE_STR.put("募集中", "1");
        STATE_STR.put("設定中", "2");
        STATE_STR.put("対戦中", "3");
        STATE_STR.put("観戦中", "4");
        STATE_STR.put("退席中", "5");
        STATE_STR.put("ホスト募集中", "6");

        IM_STR.put("0", "可");
        IM_STR.put("1", "対戦中拒否");
        IM_STR.put("2", "拒否");

        IM_STR.put("可", "0");
        IM_STR.put("対戦中拒否", "1");
        IM_STR.put("拒否", "2");
    }

    /**
     * すべてのフィールドを指定してユーザー固有の情報のインスタンスを生成します。
     * 
     * @param publicName
     *            ユーザーの公開名
     * @param UID
     *            一意のID
     * @param myIP
     *            生のIPアドレス
     * @param state
     *            ユーザーの状態
     * @param IMPolicy
     *            インスタントメッセージの受け取りポリシー
     * @param publicMessage
     *            公開メッセージ
     * @param publicShortMessage
     *            公開ショートメッセージ
     */
    public UserInfo(String publicName, String UID, byte[] myIP,
            Short casterPort, Byte state, Byte IMPolicy, Byte IPHideFlag,
            String publicMessage, String publicShortMessage) {
        this.publicName = publicName;
        this.UID = UID;
        this.ipAddress = myIP;
        this.casterPort = casterPort;
        this.state = new AtomicInteger(state);
        this.IMPolicy = IMPolicy;
        this.IPHideFlag = IPHideFlag;
        this.publicMessage = publicMessage;
        this.publicShortMessage = publicShortMessage;
    }

    /**
     * 公開名を返す。
     * 
     * @return このインスタンスが保持している公開名
     */
    public String getPublicName() {
        return publicName;
    }

    /**
     * ユニークIDを返す。
     * 
     * @return このインスタンスが保持しているユニークID
     */
    public String getUID() {
        return UID;
    }

    /**
     * 生のIPアドレスを返す。
     * 
     * @return このインスタンスが保持しているIPアドレス
     */
    public byte[] getIpAddress() {
        return ipAddress;
    }

    /**
     * Caster待ちうけポート番号を返す
     * 
     * @return
     */
    public int getCasterPort() {
        int ret = casterPort;
        if (ret < 0)
            ret += 0x10000;
        return ret;
    }

    /**
     * IM受け取りポリシーを返す。
     * 
     * @return このインスタンスが保持しているIM受け取りポリシー
     */
    public Byte getIMPolicy() {
        return IMPolicy;
    }

    /**
     * 公開メッセージを返す。
     * 
     * @return このインスタンスが保持している公開メッセージ
     */
    public String getPublicMessage() {
        return publicMessage;
    }

    /**
     * 公開名を返す。
     * 
     * @return このインスタンスが保持している公開ショートメッセージ
     */
    public String getPublicShortMessage() {
        return publicShortMessage;
    }

    /**
     * <h1>toString</h1> <h2>public String toString()</h2>
     * <p>
     * オブジェクトの文字列表現を返します。toString メソッドはこのオブジェクトを「テキストで表現する」文字列を返します。
     * </p>
     * <p>
     * publicName、UID、ipAddress、IMPolicy、IPHideFlag、publicMessage、
     * publicShortMessageを'/'で区切った文字列を返します。
     * ただし、ipAddressは"***.***.***.***"の形式で返されます。(IPv4のみ対応)
     * </p>
     * <p>
     * このメソッドは次の値と等しい文字列を返します。
     * </p>
     * <code>
     * ipString = ipAddress[0] + "." + ipAddress[1] + "." + ipAddress[2] + "." + ipAddress[3];
     * "[publicName/UID/ipAddress/IMPolicy/IPHideFlag/publicMessage/publicShortMessage] "+
     * publicName + "/" + UID + "/" + ipString + "/" + IMPolicy + "/" + IPHideFlag +  "/" + publicMessage + "/" + publicShortMessage;
     * </code>
     * 
     * @return このオブジェクトの文字列表現
     */
    @Override
    public String toString() {
        String ipString = ipAddress[0] + "." + ipAddress[1] + "."
                + ipAddress[2] + "." + ipAddress[3];
        return "[publicName/UID/ipAddress/IMPolicy/IPHideFlag/publicMessage/publicShortMessage] "
                + publicName
                + "/"
                + UID
                + "/"
                + ipString
                + "/"
                + IMPolicy
                + "/"
                + IPHideFlag
                + "/"
                + publicMessage
                + "/"
                + publicShortMessage;
    }

    /**
     * <h1>getIPHideFlag</h1> <h2>このユーザーのIP非表示設定を取得する</h2>
     * <p>
     * このユーザーのIP非表示設定を取得します。
     * </p>
     * 
     * @return IP非表示設定
     */
    public Byte getIPHideFlag() {
        return this.IPHideFlag;
    }

    /**
     * <h1>setState</h1> <h2>このユーザーの状態を変更します</h2>
     * <p>
     * このインスタンスが示すユーザーのステートを新しいステートstateに変更します。
     * </p>
     * <p>
     * 変更操作はAtomicInteger.setを用いてアトミックに行われます。
     * </p>
     * 
     * @param state
     *            新しいユーザーの状態
     */
    public void setState(Byte state) {
        this.state.set(state.intValue());
    }

    /**
     * <h1>getState</h1> <h2>このユーザーの状態を取得します</h2>
     * <p>
     * このインスタンスが示すユーザーのステートを取得します。
     * </p>
     * <p>
     * ステートはAtomicIntegerで管理されています。このメソッドは内部でAtomicInteger.getを呼び出すだけです。
     * <p>
     * 
     * @return 現在の状態
     */
    public Byte getState() {
        return state.byteValue();
    }

    /**
     * IMPolicy定数配列の要素を取得します。
     * 
     * @param i
     *            対応する番号
     * @return iに対応する文字列
     */
    public static String getIMPolicyString(int i) {
        return IM_STR.get(String.valueOf(i));
    }

    /**
     * IMPolicy定数配列の要素を取得します。
     * 
     * @param key
     *            対応するキー
     * @return keyに対応する文字列
     */
    public static String getIMPolicyString(String key) {
        return IM_STR.get(key);
    }

    /**
     * 状態定数配列の要素を取得します。
     * 
     * @param i
     *            対応する番号
     * @return iに対応する文字列
     */
    public static String getStateString(int i) {
        return STATE_STR.get(String.valueOf(i));
    }

    public static int getState(String state) {
        return Integer.parseInt(STATE_STR.get(state));
    }

    /**
     * 状態定数配列の要素を取得します。
     * 
     * @param key
     *            対応するキー
     * @return keyに対応する文字列
     */
    public static String getStateString(String key) {
        return STATE_STR.get(key);
    }
}
