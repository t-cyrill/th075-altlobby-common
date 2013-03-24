package jp.dip.th075altlobby.imo.Data.communication;

/**
 * バージョンのレスポンスをラップするクラス
 * 
 * @author Cyrill
 */
public class VersionResponse {
    private final short protocol_version, server_version;

    public VersionResponse(short protocol_version, short server_version) {
        this.protocol_version = protocol_version;
        this.server_version = server_version;
    }

    public short getProtocolVersion() {
        return this.protocol_version;
    }

    public short getServerVersion() {
        return this.server_version;
    }
}
