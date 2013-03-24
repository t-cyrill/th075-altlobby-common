package jp.dip.th075altlobby.imo.Data.communication;

/**
 * 通信用にラップされたデータを示します。
 * CommunicationDataInputおよびCommunicationDataOutputにてつかわれるラップされたデータです。
 * CommunicationDataOutputはSocketのOutputStreamにInteger, Byte,
 * byte[]としてデータを送信します。 CommunicationDataInputはSocketのInputStreamからInteger, Byte,
 * byte[]として取り出します。
 * 詳細な変換のルールは、CommunicationDataInputおよびCommunicationDataOutputを参照してください。
 * このインスタンスは内部で保持している値を変更することができません。
 * 
 * @author Cyrill
 * 
 */
public class WrappedData {
    private final Integer length;
    private final Byte command;
    private final byte[] rawData;

    public static final byte CHECK_VERSION = 1, NEW_CONNECT = 2, BYE = 3,
            REQUEST_USERLIST = 4, MODIFY_STATE = 5, POST_INSTANTMESSAGE = 6,
            REQUEST_STORED_INSTANTMESSAGES = 7,
            POST_CLIENT_TO_CLIENT_COMMAND = 8, KEEP_ALIVE = 9,

            RESPONSE_VERSION = 64, RESPONSE_NEW_CONNECT = 65,
            RESPONSE_BYE = 66, RESPONSE_USERLIST = 67,
            RESPONSE_POST_INSTANTMESSAGE = 68, RESPONSE_MODIFY_STATE = 69,
            RESPONSE_STORED_INSTANTMESSAGES = 70, RESPONSE_KEEP_ALIVE = 71,

            JOIN = 80, LEAVE = 81, STATE_MODIFIED = 82,
            DISTRIBUTE_INSTANTMESSAGE = 83,
            DISTRIBUTED_CLIENT_TO_CLIENT_COMMAND = 84,

            ERROR_EXIT_DUMMY = -1, EXIT_DUMMY = 0;
    public static final byte ACCEPTED = 1, REFUSED = 2, NOT_EXISTS = 3,
            IO_ERROR = 4;

    /**
     * <h1>WrappedData</h1> <h2>すべての要素を指定してWrappedDataを構成する。</h2>
     * <p>
     * すべての要素を指定してWrappedDataを構成します。
     * </p>
     * <p>
     * rawDataがnullの場合、new byte[0]と同じように扱います。この時lengthは無視され再計算されます。
     * </p>
     * 
     * @param length
     *            長さ
     * @param command
     *            コマンド
     * @param rawData
     *            実データ
     */
    public WrappedData(Integer length, Byte command, byte[] rawData) {
        if (rawData == null) {
            rawData = new byte[0];
            length = rawData.length;
        }
        this.length = length;
        this.command = command;
        this.rawData = rawData;
    }

    /**
     * <h1>WrappedData</h1> <h2>コマンドと実データのみを指定してWrappedDataを構成する</h2>
     * <p>
     * コマンドと実データのみを指定してWrappedDataを構成します。
     * </p>
     * <p>
     * この呼び出しはWrappedData(rawData.length, command,
     * rawData)と同じですが、rawDataにnullを指定することはできません。
     * </p>
     * 
     * @param command
     *            送信するコマンド。WrappedDataのstaticフィールドで定義された定数を使います。
     * @param rawData
     *            送信するデータ。nullを指定することはできません。
     */
    public WrappedData(Byte command, byte[] rawData) {
        this(rawData.length, command, rawData);
    }

    /**
     * このWrappedDataが保持している長さを取得する
     * 
     * @return
     */
    public Integer getLength() {
        return this.length;
    }

    /**
     * このWrappedDataが保持しているコマンドを取得する
     * 
     * @return
     */
    public Byte getCommand() {
        return this.command;
    }

    /**
     * このWrappedDataが保持している実データを取得する
     * 
     * @return
     */
    public byte[] getRawData() {
        return this.rawData;
    }
}
