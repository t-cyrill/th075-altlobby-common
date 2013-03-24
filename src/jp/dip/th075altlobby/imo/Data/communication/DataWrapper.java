package jp.dip.th075altlobby.imo.Data.communication;

import java.io.ByteArrayOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class DataWrapper {
    /**
     * 現在のユーザー一覧を取得するためのデータをbyte[]にラップする。
     * 
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapRequestUserlistModified(int lastID)
            throws IOException {
        return wrapInteger(lastID);
    }

    /**
     * 現在のインスタンスメッセージを取得するためのデータをbyte[]にラップする。
     * 
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapRequestInstantMessageModified(int lastID)
            throws IOException {
        return wrapInteger(lastID);
    }

    /**
     * <h1>wrapString</h1> <h2>単一のStringをbyte配列にラップする</h2>
     * <p>
     * 単一のStringをCommunicationDataOutput.sendで送信可能なbyte配列にラップする汎用メソッドです。
     * </p>
     * 
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws UTFDataFormatException
     *             引数文字列が65536byteを超える場合
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static byte[] wrapString(String s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(s);

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapPostInstantMessage</h1> <h2>インスタントメッセージ送信用データをbyte[]にラップする</h2>
     * <p>
     * インスタントメッセージ送信用データをCommunicationDataOutput.sendで送信可能なbyte配列にラップします。
     * </p>
     * <p>
     * このメソッドは、DataOutput.writeUTFを呼び出し、to,
     * messageを、DataOutput.writeByteを呼び出しcolorをByteArrayに書き込みます。
     * </p>
     * <p>
     * このメソッドの戻り値は、そのByteArrayが保持しているbyte配列です。
     * </p>
     * <p>
     * 文字列にnullを渡すことはできません。nullを渡した場合、NullPointerExceptionを発生させます。
     * </p>
     * 
     * @param to
     *            送信先を表す文字列
     * @param message
     *            送信するメッセージ
     * @param color
     *            表示色
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws UTFDataFormatException
     *             いずれかの引数文字列が65536byteを超える場合
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapPostInstantMessage(String to, String message,
            byte color) throws UTFDataFormatException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(to);
        dos.writeUTF(message);
        dos.writeByte(color);

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * Integerをbyte[]にラップする。
     * 
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapInteger(int i) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(i);

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapByte</h1> <h2>Byteをbyte[]にラップする</h2>
     * <p>
     * 単一のByteをCommunicationDataOutput.sendで送信可能なbyte配列にラップします。
     * </p>
     * <p>
     * このメソッドはDataOutputStream.writeByteを呼びだしByteArrayに書き出します。
     * このメソッドの戻り値は、そのByteArrayが保持しているbyte配列です。
     * </p>
     * 
     * @param b
     *            対象となる単一のbyte
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapByte(byte b) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeByte(b);

        byte[] bA = baos.toByteArray();
        dos.close();

        return bA;
    }

    /**
     * RESPONSE_VERSION用にデータをbyte[]にラップする
     * 
     * @param protocolVersion
     *            サーバーが対応しているプロトコルのバージョン
     * @param serverVersion
     *            サーバーアプリケーションのバージョン
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapResponseVersion(short protocolVersion,
            short serverVersion) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeShort(protocolVersion);
        dos.writeShort(serverVersion);

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>NEW_CONNECT用にデータをbyte[]にラップする。</h1>
     * <p>
     * このメソッドは、DataOutput.writeUTFを呼び出し、publicName, publicMessage,
     * publicShortMessageを、 DataOutput.writeByteを呼び出しIMPolicy,
     * IPHideFlagをByteArrayに書き込みます。
     * </p>
     * <p>
     * このメソッドの戻り値は、そのByteArrayが保持しているbyte配列です。
     * </p>
     * <p>
     * 文字列にnullを渡すことはできません。nullを渡した場合、NullPointerExceptionを発生させます。
     * </p>
     * 
     * @param publicName
     *            公開名
     * @param publicMessage
     *            公開メッセージ
     * @param publicShortMessage
     *            公開ショートメッセージ
     * @param IMPolicy
     *            IM公開ポリシー
     * @param IPHideFlag
     *            IP非公開設定
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws UTFDataFormatException
     *             いずれかの引数文字列が65536byteを超える場合
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapNewConnect(String publicName,
            String publicMessage, String publicShortMessage, Short port,
            Byte IMPolicy, Byte IPHideFlag) throws UTFDataFormatException,
            IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(publicName);
        dos.writeUTF(publicMessage);
        dos.writeUTF(publicShortMessage);
        dos.writeShort(port);
        dos.writeByte(IMPolicy);
        dos.writeByte(IPHideFlag);

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapNewConnected</h1> <h2>NEW_CONNECTED用にデータをbyte[]にラップする。</h2>
     * <p>
     * NEW_CONNECTED用にデータをbyte[]にラップします。
     * </p>
     * <p>
     * このメソッドは、DataOutput.writeUTFとDataOutput.writeとDataOutput.
     * writeShortとDataOutput.writeByteを呼び出し UserInfoの各フィールドをByteArrayに書き込みます。
     * </p>
     * <p>
     * このメソッドの戻り値は、そのByteArrayが保持しているbyte配列です。
     * </p>
     * <p>
     * 文字列にnullを渡すことはできません。nullを渡した場合、NullPointerExceptionを発生させます。
     * </p>
     * 
     * @param info
     *            配信する接続されたユーザーに関する情報
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws UTFDataFormatException
     *             いずれかの引数文字列が65536byteを超える場合
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapNewConnected(UserInfo info) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(info.getPublicName());
        dos.writeUTF(info.getUID());
        dos.write(info.getIpAddress(), 0, 4);
        dos.writeShort(info.getCasterPort());
        dos.writeByte(info.getIMPolicy());
        dos.writeByte(info.getIPHideFlag());
        dos.writeUTF(info.getPublicMessage());
        dos.writeUTF(info.getPublicShortMessage());

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapResponseUserlist</h1> <h2>RESPONSE_USERLIST用にデータをbyte[]にラップする。</h2>
     * <p>
     * RESPONSE_USERLIST用にデータをbyte[]にラップします。
     * </p>
     * <p>
     * このメソッドは、connectedUsers.sizeの数だけデータを送信します。
     * </p>
     * <p>
     * 各ClientInfoをDataOutput.writeUTFとDataOutput.writeとDataOutput.
     * writeByteを呼び出し UserInfoの各フィールドをByteArrayに書き込みます。
     * </p>
     * <p>
     * ただし、ClientInfo.getUserInfoの戻り値がnullの要素は除外されます。
     * </p>
     * <p>
     * このメソッドの戻り値は、そのByteArrayが保持しているbyte配列です。
     * </p>
     * <p>
     * ClientInfoの文字列にnullを渡すことはできません。nullを渡した場合、NullPointerExceptionを発生させます。
     * </p>
     * 
     * @param connectedUsers
     *            ユーザーの情報が格納されたMap
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws UTFDataFormatException
     *             いずれかの引数文字列が65536byteを超える場合
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapResponseUserlist(
            Map<Integer, ClientInfo> connectedUsers) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        DataOutputStream dos2 = new DataOutputStream(baos2);

        int counter = 0;
        // Mapを走査する
        Iterator<ClientInfo> it = connectedUsers.values().iterator();
        while (it.hasNext()) {
            ClientInfo clientInfo = it.next();
            UserInfo info = clientInfo.getUserInfo();

            if (info != null) {
                dos.writeUTF(info.getPublicName());
                dos.writeUTF(info.getUID());
                dos.write(info.getIpAddress(), 0, 4);
                dos.writeShort(info.getCasterPort());
                dos.writeByte(info.getState());
                dos.writeByte(info.getIMPolicy());
                dos.writeByte(info.getIPHideFlag());
                dos.writeUTF(info.getPublicMessage());
                dos.writeUTF(info.getPublicShortMessage());
                counter++;
            }
        }
        dos.close();

        // 最初に要素数を書き出す
        dos2.writeInt(counter);
        dos2.write(baos.toByteArray());

        byte[] b = baos2.toByteArray();
        dos2.close();

        return b;
    }

    /**
     * <h1>wrapModifyUserState</h1> <h2>ユーザー状態変更用にデータをラップする</h2>
     * <p>
     * ユーザー状態変更用にデータをCommunicationDataOutput.sendで送信可能なbyte配列にラップします。
     * このメソッドはwrapByteのエイリアスです。
     * </p>
     * 
     * @param state
     *            新しいユーザーの状態
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力エラーが発生した場合
     * 
     */
    public static byte[] wrapModifyUserState(byte state) throws IOException {
        return wrapByte(state);
    }

    /**
     * <h1>wrapStateModified</h1> <h2>ユーザー状態更新の通知情報をラップする</h2>
     * <p>
     * ユーザー状態更新の通知情報をCommunicationDataOutput.sendで送信可能なbyte配列にラップします。
     * このメソッドはUIDをDataOutputStream
     * .writeUTFにより書き出し、DataOutputStream.writeByteによりstateをByteArrayに書き出します。
     * </p>
     * <p>
     * このメソッドの戻り値はそのByteArrayが保持しているbyte配列です。
     * </p>
     * <p>
     * UIDにnullを渡すことはできません。nullを渡した場合、NullPointerExceptionを発生させます。
     * また正しくないUIDを設定した場合の動作は保証されません。おそらくクライアントはこの情報を破棄するでしょう。
     * </p>
     * 
     * @param UID
     *            ユーザーを特定できる一意のID
     * @param state
     *            新しいユーザーの状態
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力エラーが発生した場合
     */
    public static byte[] wrapStateModified(String UID, byte state)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(UID);
        dos.writeByte(state);

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapDistributedIM</h1> <h2>クライアントに配信するIMをbyte配列にラップする</h2>
     * <p>
     * クライアントに配信するIMをCommunicationDataOutput.sendで送信可能なbyte配列にラップする
     * </p>
     * 
     * @param dIM
     *            配信されるIM
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static byte[] wrapDistributedIM(InstantMessage dIM)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(dIM.getFrom());
        dos.writeUTF(dIM.getTo());
        dos.writeUTF(dIM.getMessage());
        dos.writeLong(dIM.getTime());
        dos.writeByte(dIM.getColor());

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapResponseStoredInstantMessages</h1> <h2>
     * 保存されたインスタントメッセージのコレクションをCommunicationDataOutput.sendで送信可能なbyte配列にラップする。</h2>
     * <p>
     * サーバーに保存されたインスタントメッセージのコレクションをCommunicationDataOutput.
     * sendで送信可能なbyte配列にラップします。
     * </p>
     * <p>
     * responseCodeには、WrappedDataで定義された定数、ACCEPTED, REFUSED, NOT_EXIST,
     * IO_ERRORのいずれかを指定します。これ以外の応答コードはクライアントが理解できないためおそらく破棄されます。
     * </p>
     * <p>
     * REFUSED, NOT_EXIST,
     * IO_ERRORのいずれかが指定される場合、IMを返す必要がないためInstantMessagesにはnullを指定することができます。
     * ACCEPTEDの場合でもnullを指定することは可能ですが、クライアントが期待する応答ではないでしょう。
     * </p>
     * <p>
     * このメソッドは、まずDataOutputStream.writeByteによりresponseCodeをByteArrayに書き出します。
     * </p>
     * <p>
     * 次にInstantMessagesがnullでない場合、DataOutputStream.
     * writeByteによりInstantMessagesの要素数を書き出します。nullを指定した場合0が書き出します。
     * </p>
     * <p>
     * 最後に、書き出した要素数の数だけループを行い、InstantMessagesをwrapDistributedIMと同じ方法で書き出します。
     * このメソッドの戻り値は、このByteArrayが保持しているbyte配列です。
     * </p>
     * 
     * @param responseCode
     *            クライアントに通知する応答コード
     * @param InstantMessages
     *            送信の対象となるInstantMessageのコレクション
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static byte[] wrapResponseStoredInstantMessages(byte responseCode,
            Collection<InstantMessage> InstantMessages) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeByte(responseCode);
        if (InstantMessages == null) {
            dos.writeByte(0);
        } else {
            int collectionSize = InstantMessages.size();
            if (collectionSize == 0) {
                dos.writeByte(0);
            } else {
                dos.writeByte(collectionSize);

                Queue<InstantMessage> IMQueue = new ArrayDeque<InstantMessage>(
                        InstantMessages);
                while (!IMQueue.isEmpty()) {
                    InstantMessage current = IMQueue.poll();
                    dos.writeUTF(current.getFrom());
                    dos.writeUTF(current.getTo());
                    dos.writeUTF(current.getMessage());
                    dos.writeLong(current.getTime());
                    dos.writeByte(current.getColor());
                }
            }
        }

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapPostClientToClientCommand</h1> <h2>
     * サーバーに送るクライアント間コマンドをCommunicationDataOutput.sendで送信可能なbyte配列にラップする。</h2>
     * <p>
     * サーバーに送るクライアント間コマンドをCommunicationDataOutput.sendで送信可能なbyte配列にラップします。
     * </p>
     * <p>
     * このメソッドは、DataOutputStream.writeUTFによりtoUIDを、DataOutputStream.
     * writeByteによりcommandをByteArrayに書き出します。
     * </p>
     * <p>
     * このメソッドの戻り値は、そのByteArrayが保持しているbyte配列です。
     * </p>
     * <p>
     * このメソッドは<strong>クライアントがサーバーに送る際に</strong>使用します。
     * サーバーへ送られるデータにはfromUIDは含まれません。サーバー側が算出します。
     * </p>
     * 
     * @param toUID
     *            配信先UID
     * @param command
     *            配信するコマンド
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static byte[] wrapPostClientToClientCommand(String toUID,
            byte command) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(toUID);
        dos.writeByte(command);

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }

    /**
     * <h1>wrapClientToClientCommand</h1> <h2>
     * サーバーが転送するクライアント間コマンドをCommunicationDataOutput.sendで送信可能なbyte配列にラップする。</h2>
     * <p>
     * サーバーが転送するクライアント間コマンドををCommunicationDataOutput.sendで送信可能なbyte配列にラップします。
     * </p>
     * <p>
     * このメソッドは、DataOutputStream.writeUTFによりfromUIDとtoUIDを、DataOutputStream.
     * writeByteによりcommandをByteArrayに書き出します。
     * </p>
     * <p>
     * このメソッドの戻り値は、そのByteArrayが保持しているbyte配列です。
     * </p>
     * <p>
     * このメソッドは<strong>サーバーがクライアントに対して送る場合に</strong>使用します。
     * サーバーから送られるデータにはClientToClientの完全なデータが含まれます。
     * </p>
     * 
     * @param c
     *            転送するコマンド
     * @return CommunicationDataOutput.sendで送信可能なbyte配列
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public static byte[] wrapClientToClientCommand(ClientToClientCommand c)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(c.getFromUID());
        dos.writeUTF(c.getToUID());
        dos.writeByte(c.getCommand());

        byte[] b = baos.toByteArray();
        dos.close();

        return b;
    }
}
