package com.git.wuqf.xiaokuo.remoting;

/**
 * Created by wuqf on 17-2-24.
 */
public interface Client extends EndPoint, Channel, Resetable {
    void reconnect() throws RemotingException;
}
