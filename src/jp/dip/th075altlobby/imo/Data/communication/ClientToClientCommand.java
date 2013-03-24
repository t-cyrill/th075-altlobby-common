package jp.dip.th075altlobby.imo.Data.communication;

/**
 * <h1>ClientToClientCommand</h1> <h2>クライアント間のコマンドを表現するクラス</h2>
 * <p>
 * クライアント間で送受信されるコマンドを表現するクラスです。
 * </p>
 * <p>
 * インスタンスは送信元UID, 送信先UID, 送信するコマンドの3つをフィールドとして持ちます。
 * </p>
 * <p>
 * クライアント間コマンドは送信先UIDが存在すれば必ず配信され、配信に失敗した場合はそのことを送信元クライアントに伝えます。
 * ただし、配信されたクライアントが要求に答えるかは保障されません。
 * </p>
 * <p>
 * このクラスのインスタンスは不可変です。
 * </p>
 * 
 * @author Cyrill
 */
public class ClientToClientCommand {
    private final String fromUID;
    private final String toUID;
    private final byte command;

    public static final byte NULL = 0, POST_FIGHT = 1,
            ACCEPTED_POST_FIGHT = 64, ALREADY_ACCEPTED_POST_FIGHT = 65,
            NON_GATHERING = 66, PROCESS_START_ERROR = 67;

    /**
     * <h1>ClientToClientCommand</h1> <h2>インスタンスを生成する</h2>
     * <p>
     * このクラスのインスタンスを生成します。送信元と送信先を一意に特定できるfromUID,
     * toUIDと送信するコマンドを示すbyteのcommandを引数に取ります。
     * </p>
     * <p>
     * fromUIDおよびtoUIDをnullにすることはできません。その場合、NullPointerExceptionを発生させます。
     * </p>
     * <p>
     * 生成されるインスタンスは不可変です。
     * </p>
     * 
     * @param fromUID
     *            配信元UID
     * @param toUID
     *            配信先UID
     * @param command
     *            コマンド
     */
    public ClientToClientCommand(String fromUID, String toUID, byte command) {
        if (fromUID == null)
            throw new NullPointerException("fromUIDにnullを指定することはできません。");
        if (toUID == null)
            throw new NullPointerException("toUIDにnullを指定することはできません。");

        this.fromUID = fromUID;
        this.toUID = toUID;
        this.command = command;
    }

    /**
     * <h1>getFromUID</h1> <h2>このインスタンスが保持している送信元UIDを示す文字列を返す</h2>
     * 
     * @return 送信元UID
     */
    public String getFromUID() {
        return fromUID;
    }

    /**
     * <h1>getToUID</h1> <h2>このインスタンスが保持している送信先UIDを示す文字列を返す</h2>
     * 
     * @return 送信先UID
     */
    public String getToUID() {
        return toUID;
    }

    /**
     * <h1>getCommand</h1> <h2>このインスタンスが保持しているコマンドを示すbyteを返す</h2>
     * 
     * @return コマンド
     */
    public byte getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return getFromUID() + "/" + getToUID() + "/" + getCommand();
    }
}
