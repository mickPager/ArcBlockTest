// IAidlService.aidl
package cn.mick.app.arcblocktest;

import cn.mick.app.arcblocktest.IMessage;

// Declare any non-default types here with import statements

interface IAidlService {

    void sendMessage(inout IMessage message);
}